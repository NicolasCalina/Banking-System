package org.poo.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.poo.utils.Utils;

@Getter
@Setter
public class Card {
    private String cardNumber;
    private String status;
    @JsonIgnore
    private int isOneTimeUse;
    @JsonIgnore
    private int isFrozen;

    public Card() {
        this.cardNumber = Utils.generateCardNumber();
        this.status = "active";
        this.isOneTimeUse = 0;
        this.isFrozen = 0;
    }

    public Card(final Card card) {
        this.cardNumber = card.getCardNumber();
        this.status = card.getStatus();
        this.isOneTimeUse = card.getIsOneTimeUse();
        this.isFrozen = card.getIsFrozen();
    }
}
