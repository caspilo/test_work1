package org.example.interfaces;

public interface Contribution {

    default void replenishment(double replenishmentValue) {
        setBalance(getBalance() + replenishmentValue);
    }

    default void closeContribution() {
        System.out.println("Contribution has been closed");
    }

    double getBalance();

    void setBalance(double balance);
}
