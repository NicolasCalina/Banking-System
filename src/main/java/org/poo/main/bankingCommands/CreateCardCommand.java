package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.main.*;
import org.poo.main.Transaction.CreateCardTransactions;

import java.util.ArrayList;

@Getter
@Setter
public class CreateCardCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String account;
    private int isOneTimeUse;
    private CommandInput commandInput;

    public CreateCardCommand(final ArrayList<User> users, final String email, final String account,
                             final int isOneTimeUse, final CommandInput commandInput) {
        this.users = users;
        this.email = email;
        this.account = account;
        this.isOneTimeUse = isOneTimeUse;
        this.commandInput = commandInput;
    }
    /**
     * This method creates a card for a user.
     */
    public void execute() {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(this.account)) {
                        Card newCard = new Card();
                        if (isOneTimeUse == 1) {
                            newCard.setIsOneTimeUse(1);
                        }
                        account.getCards().add(newCard);
                        CreateCardTransactions createCard = new
                                CreateCardTransactions(account.getIBAN(),
                                newCard.getCardNumber(), user.getEmail(),
                                "New card created", commandInput.getTimestamp());
                        user.getTransactions().add(createCard);
                    }
                }
            }
        }
    }

}
