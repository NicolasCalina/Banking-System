package org.poo.main;

import lombok.Getter;
import lombok.Setter;
import org.poo.utils.Utils;

@Getter
@Setter
public class Card {
    private String cardNumber;
    private String status;

    public Card(){
        this.cardNumber = Utils.generateCardNumber();
        this.status = "active";
    }

    public Card(Card card){
        this.cardNumber = card.getCardNumber();
        this.status = card.getStatus();
    }
}
