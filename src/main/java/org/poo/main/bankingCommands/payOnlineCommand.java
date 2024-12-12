package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Card;
import org.poo.main.Command;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class payOnlineCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String cardNumber;
    private double amount;
    boolean foundCard;

    public payOnlineCommand(ArrayList<User> users, String email, String cardNumber , double amount){
        this.users = users;
        this.email = email;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.foundCard = false;
    }

    public void execute(){
        for (User user : users ){
            if ( user.getEmail().equals( email ) ){
                for ( Account account : user.getAccounts() ){
                    for ( Card card : account.getCards() ){
                        if ( card.getCardNumber().equals( cardNumber) ){
                            foundCard = true;
                            if ( card.getIsOneTimeUse() == 1 ){
                                account.getCards().remove(card);
                                Card newCard = new Card();
                                newCard.setIsOneTimeUse(1);
                                account.getCards().add(newCard);
                            }
                            if ( account.getBalance() > amount )
                                account.setBalance(account.getBalance() - amount);
                        }
                    }
                }
            }
        }
    }
}
