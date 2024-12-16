package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class payOnlineTransactions extends Transactions{
    private double amount;
    private String commerciant;

    public payOnlineTransactions(double amount, String commerciant, String description, int timestamp){
        super(description, timestamp);
        this.amount = amount;
        this.commerciant = commerciant;
    }

    public payOnlineTransactions(payOnlineTransactions transaction){
        super(transaction.getDescription(), transaction.getTimestamp());
        this.amount = transaction.amount;
        this.commerciant = transaction.commerciant;
    }

    public Transactions copyTransaction(){
        return new payOnlineTransactions(this);
    }

}
