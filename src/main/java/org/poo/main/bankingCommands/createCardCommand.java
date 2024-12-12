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
public class createCardCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String account;
    private int isOneTimeUse;

    public createCardCommand(ArrayList<User> users, String email, String account, int isOneTimeUse) {
        this.users = users;
        this.email = email;
        this.account = account;
        this.isOneTimeUse = isOneTimeUse;
    }

    public void execute(){
        for ( User user : users ){
            if ( user.getEmail().equals(email ) ){
                for ( Account account : user.getAccounts() ){
                    if ( account.getIBAN().equals(this.account) ){
                        Card newCard = new Card();
                        if ( isOneTimeUse == 1 ){
                            newCard.setIsOneTimeUse(1);
                        }
                        account.getCards().add(newCard);
                    }
                }
            }
        }
    }

}
