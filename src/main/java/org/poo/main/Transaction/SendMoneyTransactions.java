package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class SendMoneyTransactions extends Transactions {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final String amount;
    private final String transferType;

    public SendMoneyTransactions(final String senderIBAN, final String receiverIBAN,
                                 final double amount, final String transferType,
                                 final int timestamp,
                                 final String description, final String currency) {
        super(description, timestamp);
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount + " " + currency;
        this.transferType = transferType;
    }

    public SendMoneyTransactions(final SendMoneyTransactions transaction) {
        super(transaction.getDescription(), transaction.getTimestamp());
        this.senderIBAN = transaction.getSenderIBAN();
        this.receiverIBAN = transaction.getReceiverIBAN();
        this.amount = transaction.getAmount();
        this.transferType = transaction.getTransferType();
    }
    /**
     * Copy the transaction
     * @return a new transaction with the same values
     */
    public Transactions copyTransaction() {
        return new SendMoneyTransactions(this);
    }
}
