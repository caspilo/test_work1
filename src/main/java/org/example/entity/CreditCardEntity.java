package org.example.entity;

import org.example.interfaces.Card;

public class CreditCardEntity extends BankProduct implements Card {


    private double interestRate;


    public CreditCardEntity(String name, double balance, String currency, double interestRate) {
        super(name, balance, currency);
        this.interestRate = interestRate;
    }

    public double getDebt() {
        return getBalance() * (interestRate / 100);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
