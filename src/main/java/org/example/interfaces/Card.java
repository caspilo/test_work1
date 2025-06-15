package org.example.interfaces;

public interface Card {

    default void replenishment(double replenishmentValue) {
        setBalance(getBalance() + replenishmentValue);
    }


    default void writeOff(double writeOffValue) {
        if (getBalance() < writeOffValue) throw new IllegalArgumentException("Not enough money on balance to write off");
        setBalance(getBalance() - writeOffValue);
    }

    double getBalance();

    void setBalance(double balance);

}
