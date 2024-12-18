package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public final class SplitPaymentTransaction extends Transactions {
    private final String currency;
    private final double amount;
    private final ArrayList<String> involvedAccounts;

    public SplitPaymentTransaction(final String currency, final double amount,
                                   final ArrayList<String> involvedAccounts,
                                   final String description, final int timestamp) {
        super(description, timestamp);
        this.currency = currency;
        this.amount = amount;
        this.involvedAccounts = involvedAccounts;
    }

    public SplitPaymentTransaction(final SplitPaymentTransaction transaction) {
        super(transaction.getDescription(), transaction.getTimestamp());
        this.currency = transaction.currency;
        this.amount = transaction.amount;
        this.involvedAccounts = transaction.involvedAccounts;
    }
    /**
     * Copy the transaction
     * @return a new transaction with the same values
     */
    public Transactions copyTransaction() {
        return new SplitPaymentTransaction(this);
    }
}
