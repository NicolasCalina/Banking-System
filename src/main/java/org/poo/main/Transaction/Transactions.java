package org.poo.main.Transaction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Transactions {
    private int timestamp;
    private String description;

    public Transactions copyTransaction(){
        return new Transactions(this);
    }

    public Transactions(String description, int timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    public Transactions(Transactions transactions) {
        this.description = transactions.getDescription();
        this.timestamp = transactions.getTimestamp();
    }

    public Transactions(){

    }
}
