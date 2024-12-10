package org.poo.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

import java.util.ArrayList;

@Getter
@Setter
@JsonPropertyOrder({"IBAN", "balance", "currency", "type", "cards"})
public class Account {
    @JsonProperty("IBAN")
    private String IBAN;
    private double balance;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("type")
    private String type;
    private ArrayList<Card> cards;
    // array de cards

    public Account(CommandInput input){
        this.IBAN = Utils.generateIBAN();
        this.balance = 0;
        this.currency = input.getCurrency();
        this.type = input.getAccountType();
        this.cards = new ArrayList<>();
    }

    public Account(Account account){
        this.IBAN = account.getIBAN();
        this.balance = account.getBalance();
        this.currency = account.getCurrency();
        this.type = account.getType();
        this.cards = new ArrayList<>();
        for( int i = 0 ; i < account.getCards().size(); i++){
            this.cards.add(new Card(account.getCards().get(i)));
        }
    }

}
