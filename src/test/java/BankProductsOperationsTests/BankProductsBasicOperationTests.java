package BankProductsOperationsTests;

import org.example.entity.ContributionEntity;
import org.example.entity.CreditCardEntity;
import org.example.entity.DebetCard;
import org.example.entity.DebetCurrencyCard;
import org.example.interfaces.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;

@Execution(ExecutionMode.CONCURRENT)
public class BankProductsBasicOperationTests {

    private static DebetCard debetCard;
    private static DebetCurrencyCard debetCurrencyCard;
    private static CreditCardEntity creditCard;
    private static ContributionEntity contribution;
    private static List<Card> cardList;


    @BeforeAll
    static void init() {
        debetCard = new DebetCard("DebetCard", 100, "RUB");
        debetCurrencyCard = new DebetCurrencyCard("DebetCurrencyCard", 200, "USD");
        creditCard = new CreditCardEntity("CreditCard", 1000, "RUB", 40);
        contribution = new ContributionEntity("Contribution", 300, "RUB", false);
        cardList = List.of(debetCard, debetCurrencyCard, creditCard);
    }

    @BeforeEach
    void reinit() {
        debetCard.setBalance(100);
        debetCurrencyCard.setBalance(200);
        creditCard.setBalance(1000);
        contribution.setBalance(300);
        contribution.setClosed(false);
    }

    @Test
    void getBalanceBankProductsTest() {
        Assertions.assertEquals(100, debetCard.getBalance());
        Assertions.assertEquals(200, debetCurrencyCard.getBalance());
        Assertions.assertEquals(1000, creditCard.getBalance());
        Assertions.assertEquals(300, contribution.getBalance());
    }

    @Test
    void getNameBankProductsTest() {
        Assertions.assertEquals("DebetCard", debetCard.getName());
        Assertions.assertEquals("DebetCurrencyCard", debetCurrencyCard.getName());
        Assertions.assertEquals("CreditCard", creditCard.getName());
        Assertions.assertEquals("Contribution", contribution.getName());

    }

    @Test
    void getCurrencyBankProductsTest() {
        Assertions.assertEquals("RUB", debetCard.getCurrency());
        Assertions.assertEquals("USD", debetCurrencyCard.getCurrency());
        Assertions.assertEquals("RUB", creditCard.getCurrency());
        Assertions.assertEquals("RUB", contribution.getCurrency());
    }

    @Test
    void replenishmentBankProductsTest() {
        for (Card card : cardList) {
            card.replenishment(50);
        }
        contribution.replenishment(50);

        Assertions.assertEquals(150, debetCard.getBalance());
        Assertions.assertEquals(250, debetCurrencyCard.getBalance());
        Assertions.assertEquals(1050, creditCard.getBalance());
        Assertions.assertEquals(350, contribution.getBalance());
    }

    @Test
    void writeOffCardsTest() {
        for (Card card : cardList) {
            card.writeOff(50);
        }
        Assertions.assertEquals(50, debetCard.getBalance());
        Assertions.assertEquals(150, debetCurrencyCard.getBalance());
        Assertions.assertEquals(950, creditCard.getBalance());
    }

    @Test
    void getDebtForCreditCardTest() {
        Assertions.assertEquals(400, creditCard.getDebt());
    }

    @Test
    void closeContributionTest() {
        contribution.closeContribution();
        Assertions.assertTrue(contribution.isClosed());
    }
    @Test
    void writeOffWithExceptionForCardsTest() {
        for (Card card : cardList) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> card.writeOff(10000));
        }
    }
    @Test
    void closeAlreadyClosedContributionTest() {
        contribution.closeContribution();
        Assertions.assertThrows(IllegalArgumentException.class, () -> contribution.closeContribution());
    }

}
