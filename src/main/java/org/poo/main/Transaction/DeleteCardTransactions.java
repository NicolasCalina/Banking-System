package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCardTransactions extends Transactions{
    private String account;
    private String card;
    private String cardHolder;

    public DeleteCardTransactions(String account, String card, String cardHolder, String description, int timestamp){
        super(description, timestamp);
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }

    public DeleteCardTransactions(DeleteCardTransactions transaction){
        super(transaction.getDescription(), transaction.getTimestamp());
        this.account = transaction.getAccount();
        this.card = transaction.getCard();
        this.cardHolder = transaction.getCardHolder();
    }

    public Transactions copyTransaction(){
        return new DeleteCardTransactions(this);
    }
}
