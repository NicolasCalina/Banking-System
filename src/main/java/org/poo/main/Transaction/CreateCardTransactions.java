package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCardTransactions extends Transactions{
    public String account;
    public String card;
    public String cardHolder;

    public CreateCardTransactions(String account, String card, String cardHolder, String description, int timestamp){
        super(description, timestamp);
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }

    public CreateCardTransactions(CreateCardTransactions transaction){
        super(transaction.getDescription(), transaction.getTimestamp());
        this.account = transaction.account;
        this.card = transaction.card;
        this.cardHolder = transaction.cardHolder;
    }

    public Transactions copyTransaction(){
        return new CreateCardTransactions(this);
    }
}
