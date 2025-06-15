package org.example.entity;

import org.example.interfaces.Contribution;

public class ContributionEntity extends BankProduct implements Contribution {

    private boolean isClosed;
    public ContributionEntity(String name, double balance, String currency, boolean isClosed) {
        super(name, balance, currency);
        this.isClosed = isClosed;
    }

    @Override
    public void closeContribution() {
        if (isClosed) throw new IllegalArgumentException("Contribution already closed");
        isClosed = true;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
