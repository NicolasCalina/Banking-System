package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class DeleteCardTransactions extends Transactions {
    private final String account;
    private final String card;
    private final String cardHolder;

    public DeleteCardTransactions(final String account,
                                  final String card, final String cardHolder,
                                  final String description,
                                  final int timestamp) {
        super(description, timestamp);
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }

    public DeleteCardTransactions(final DeleteCardTransactions transaction) {
        super(transaction.getDescription(), transaction.getTimestamp());
        this.account = transaction.getAccount();
        this.card = transaction.getCard();
        this.cardHolder = transaction.getCardHolder();
    }
    /**
     * Copy the transaction
     * @return a new transaction with the same values
     */
    public Transactions copyTransaction() {
        return new DeleteCardTransactions(this);
    }
}
