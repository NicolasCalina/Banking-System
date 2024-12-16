package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sendMoneyTransactions extends Transactions {
    private String senderIBAN;
    private String receiverIBAN;
    private String amount;
    private String transferType;

    public sendMoneyTransactions(String senderIBAN, String receiverIBAN, double amount, String transferType, int timestamp , String description, String currency) {
        super(description, timestamp);
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount + " " + currency;
        this.transferType = transferType;
    }

    public sendMoneyTransactions(sendMoneyTransactions transaction) {
        super(transaction.getDescription(), transaction.getTimestamp());
        this.senderIBAN = transaction.getSenderIBAN();
        this.receiverIBAN = transaction.getReceiverIBAN();
        this.amount = transaction.getAmount();
        this.transferType = transaction.getTransferType();
    }

    public Transactions copyTransaction() {
        return new sendMoneyTransactions(this);
    }
}
