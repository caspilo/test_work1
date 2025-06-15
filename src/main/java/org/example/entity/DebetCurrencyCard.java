package org.example.entity;

import org.example.interfaces.Card;

public class DebetCurrencyCard extends BankProduct implements Card {

    public DebetCurrencyCard(String name, double balance, String currency) {
        super(name, balance, currency);
    }
}
