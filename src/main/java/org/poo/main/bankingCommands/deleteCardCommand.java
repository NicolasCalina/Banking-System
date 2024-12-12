package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Card;
import org.poo.main.Command;
import org.poo.main.User;

import java.util.ArrayList;
import java.util.Iterator;

@Getter
@Setter
public class deleteCardCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String cardNumber;

    public deleteCardCommand(ArrayList<User> users, String email, String cardNumber) {
        this.users = users;
        this.email = email;
        this.cardNumber = cardNumber;
    }

    public void execute(){
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    Iterator<Card> iterator = account.getCards().iterator();
                    while (iterator.hasNext()) {
                        Card card = iterator.next();
                        if (card.getCardNumber().equals(cardNumber)) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
    }
}
