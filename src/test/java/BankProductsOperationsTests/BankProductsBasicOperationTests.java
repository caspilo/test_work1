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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(100, debetCard.getBalance());
        assertEquals(200, debetCurrencyCard.getBalance());
        assertEquals(1000, creditCard.getBalance());
        assertEquals(300, contribution.getBalance());
    }

    @Test
    void getNameBankProductsTest() {
        assertEquals("DebetCard", debetCard.getName());
        assertEquals("DebetCurrencyCard", debetCurrencyCard.getName());
        assertEquals("CreditCard", creditCard.getName());
        assertEquals("Contribution", contribution.getName());

    }

    @Test
    void getCurrencyBankProductsTest() {
        assertEquals("RUB", debetCard.getCurrency());
        assertEquals("USD", debetCurrencyCard.getCurrency());
        assertEquals("RUB", creditCard.getCurrency());
        assertEquals("RUB", contribution.getCurrency());
    }

    @Test
    void replenishmentBankProductsTest() {
        for (Card card : cardList) {
            card.replenishment(50);
        }
        contribution.replenishment(50);

        assertEquals(150, debetCard.getBalance());
        assertEquals(250, debetCurrencyCard.getBalance());
        assertEquals(1050, creditCard.getBalance());
        assertEquals(350, contribution.getBalance());
    }

    @Test
    void writeOffCardsTest() {
        for (Card card : cardList) {
            card.writeOff(50);
        }
        assertEquals(50, debetCard.getBalance());
        assertEquals(150, debetCurrencyCard.getBalance());
        assertEquals(950, creditCard.getBalance());
    }

    @Test
    void getDebtForCreditCardTest() {
        assertEquals(400, creditCard.getDebt());
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
