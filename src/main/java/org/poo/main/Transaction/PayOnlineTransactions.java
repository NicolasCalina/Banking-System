package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PayOnlineTransactions extends Transactions {
    private final double amount;
    private final String commerciant;

    public PayOnlineTransactions(final double amount,
                                 final String commerciant,
                                 final String description, final int timestamp) {
        super(description, timestamp);
        this.amount = amount;
        this.commerciant = commerciant;
    }

    public PayOnlineTransactions(final PayOnlineTransactions transaction) {
        super(transaction.getDescription(), transaction.getTimestamp());
        this.amount = transaction.amount;
        this.commerciant = transaction.commerciant;
    }
    /**
     * Copy the transaction
     * @return a new transaction with the same values
     */
    public Transactions copyTransaction() {
        return new PayOnlineTransactions(this);
    }

}
