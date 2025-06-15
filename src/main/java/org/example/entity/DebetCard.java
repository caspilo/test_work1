package org.example.entity;

import org.example.interfaces.Card;

public class DebetCard extends BankProduct implements Card {

    public DebetCard(String name, double balance, String currency) {
        super(name, balance, currency);
    }
}
