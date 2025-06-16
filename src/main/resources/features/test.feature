Feature: Testing weather API using WireMock

  Scenario: Positive tests with mocked cities

    Given заглушка для города "Ufa"
    When получаю текущую погоду по городу "Ufa"
    Then статус ответа равен "200"
    And парсим результат и сравниваем значения

  Scenario Outline: Negative tests with errors
    Given заглушка ошибки "<error>"
    When делаем запрос на ошибку "<error>"
    Then код ошибки соответствует "<expected_status>"
    And сообщение об ошибке равно "<expected_message>"

    Examples:

      | error       | expected_status   | expected_message            |
      | ----------- | ----------------- | --------------------------- |
      | 401         | 401               | Invalid API key             |
      | 400         | 400               | Bad Request                 |
      | 404         | 404               | Not Found                   |
      | 500         | 500               | Internal Server Error       |