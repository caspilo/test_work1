package org.example.entity;

public abstract class BankProduct {
    private String name;
    private double balance;
    private String currency;

    protected BankProduct(String name, double balance, String currency) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
