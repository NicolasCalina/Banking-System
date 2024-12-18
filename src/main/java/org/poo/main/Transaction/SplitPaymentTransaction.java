package org.poo.main.Transaction;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SplitPaymentTransaction extends Transactions{
    private String currency;
    private double amount;
    private ArrayList<String> involvedAccounts;

    public SplitPaymentTransaction(String currency, double amount, ArrayList<String> involvedAccounts, String description , int timestamp){
        super(description, timestamp);
        this.currency = currency;
        this.amount = amount;
        this.involvedAccounts = involvedAccounts;
    }

    public SplitPaymentTransaction(SplitPaymentTransaction transaction){
        super(transaction.getDescription(), transaction.getTimestamp());
        this.currency = transaction.currency;
        this.amount = transaction.amount;
        this.involvedAccounts = transaction.involvedAccounts;
    }

    public Transactions copyTransaction(){
        return new SplitPaymentTransaction(this);
    }
}
