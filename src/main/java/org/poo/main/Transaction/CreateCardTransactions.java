package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CreateCardTransactions extends Transactions {
    private final String account;
    private final String card;
    private final String cardHolder;

    public CreateCardTransactions(final String account,
                                  final String card, final String cardHolder,
                                  final String description, final int timestamp) {
        super(description, timestamp);
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }

    public CreateCardTransactions(final CreateCardTransactions transaction) {
        super(transaction.getDescription(), transaction.getTimestamp());
        this.account = transaction.account;
        this.card = transaction.card;
        this.cardHolder = transaction.cardHolder;
    }
    /**
     * Copy the transaction
     * @return a new transaction with the same values
     */
    public Transactions copyTransaction() {
        return new CreateCardTransactions(this);
    }
}
