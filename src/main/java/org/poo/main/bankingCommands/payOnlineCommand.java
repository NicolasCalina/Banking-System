package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.*;

import java.util.ArrayList;

@Getter
@Setter
public class payOnlineCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String cardNumber;
    private double amount;
    private String currency;
    private ExchangeGraph exchangeGraph;
    boolean foundCard;

    public payOnlineCommand(ArrayList<User> users, String email, String cardNumber , double amount, String currency, ExchangeGraph exchangeGraph){
        this.users = users;
        this.email = email;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.foundCard = false;
        this.currency = currency;
        this.exchangeGraph = exchangeGraph;
    }

    public void execute(){
        for (User user : users ){
            if ( user.getEmail().equals( email ) ){
                for ( Account account : user.getAccounts() ){
                    if ( !account.getCurrency().equals( currency ) ) {
                        double exchangeRate = exchangeGraph.findExchangeRate(currency, account.getCurrency());
                        amount *= exchangeRate;
                    }
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
