package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Transactions {
    private int timestamp;
    private String description;
    /**
     * Copy the transaction
     * @return a new transaction with the same values
     */
    public Transactions copyTransaction() {
        return new Transactions(this);
    }

    public Transactions(final String description, final int timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    public Transactions(final Transactions transactions) {
        this.description = transactions.getDescription();
        this.timestamp = transactions.getTimestamp();
    }

    public Transactions() {

    }
}
