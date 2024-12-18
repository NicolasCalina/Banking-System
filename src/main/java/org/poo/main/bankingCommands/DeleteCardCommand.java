package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Card;
import org.poo.main.Command;
import org.poo.main.Transaction.DeleteCardTransactions;
import org.poo.main.User;

import java.util.ArrayList;
import java.util.Iterator;

@Getter
@Setter
public class DeleteCardCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String cardNumber;
    private int timestamp;

    public DeleteCardCommand(final ArrayList<User> users, final String email,
                             final String cardNumber, final int timestamp) {
        this.users = users;
        this.email = email;
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
    }
    /**
     * This method deletes a card from a user's account.
     */
    public void execute() {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    Iterator<Card> iterator = account.getCards().iterator();
                    while (iterator.hasNext()) {
                        Card card = iterator.next();
                        if (card.getCardNumber().equals(cardNumber)) {
                            DeleteCardTransactions deleteCardTransactions = new
                                    DeleteCardTransactions(account.getIBAN(),
                                    card.getCardNumber(), user.getEmail(),
                                    "The card has been destroyed", timestamp);
                            user.getTransactions().add(deleteCardTransactions);
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
    }
}
