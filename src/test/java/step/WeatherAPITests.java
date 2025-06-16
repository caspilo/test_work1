package step;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.example.pojo.ErrorResponse;
import org.example.pojo.WeatherObject;
import wiremock.org.apache.hc.core5.http.HttpEntity;
import wiremock.org.apache.hc.core5.http.ParseException;
import wiremock.org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WeatherAPITests {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private final int port = 8080;
    private WireMockServer wireMockServer;
    //e0b2ad14814047acb6d103945251506
    @Before
    public void startWireMock() {
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor("localhost", port);
    }

    @After
    public void stopWireMock() {
        wireMockServer.stop();
    }

    @Given("заглушка для города {city}")
    void stubForCityWeather(String city) {
        stubFor(WireMock.get(urlPathEqualTo("http://api.weatherapi.com/v1/current.json?key=e0b2ad14814047acb6d103945251506$q=" + city))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile((city +".json"))
                ));
    }

    @When("получаю текущую погоду по городу {city}")
    public void getCurrentWeather(String city) throws IOException {
        client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://api.weatherapi.com/v1/current.json?key=e0b2ad14814047acb6d103945251506&q=" + city);
        response = client.execute(request);
    }

    @Then("статус ответа равен {expectedStatus}")
    public void checkResponseCode(int expectedStatus) {
        assertEquals(expectedStatus, response.getCode());
    }

    @Then("^парсим результат и сравниваем значения$")
    public void parseResultAndCompareValues() throws IOException, ParseException {
        String body = EntityUtils.toString((HttpEntity) response.getEntity());
        WeatherObject weather = objectMapper.readValue(body, WeatherObject.class);

        assertEquals("Уфа", weather.getLocation().getName());
        assertEquals(24.4, weather.getCurrent().getTempC(), 0.01);
        assertEquals("Partly cloudy", weather.getCurrent().getCondition().getText());

    }

    @Given("заглушка ошибки {int}")
    public void stubError(String errorCode) {
        stubFor(get(urlMatching("/current.json.*"))
                .willReturn(aResponse()
                        .withStatus(Integer.parseInt(errorCode.substring(0, 3)))
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("errors/" + errorCode + ".json")));
    }

    @When("делаем запрос на ошибку {errorCode}")
    public void makeErrorRequest(String errorCode) throws IOException {
        client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://api.weatherapi.com/v1/current.json?key=invalid_key");
        response = client.execute(request);
    }

    @Then("код ошибки соответствует {expectedErrorCode}")
    public void checkErrorCode(String expectedErrorCode) {
        assertEquals(Integer.parseInt(expectedErrorCode.substring(0, 3)), response.getCode());
    }

    @Then("сообщение об ошибке равно {expectedMessage}")
    public void checkErrorMessage(String expectedMessage) throws IOException, ParseException {
        String body = EntityUtils.toString((HttpEntity) response.getEntity());
        ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);

        assertEquals(expectedMessage, errorResponse.getMessage());

    }
}