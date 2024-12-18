package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.User;

import java.util.ArrayList;
import java.util.Iterator;

@Getter
@Setter
public class DeleteAccountCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String account;
    private boolean success;

    public DeleteAccountCommand(final ArrayList<User> users,
                                final String email, final String account) {
        this.users = users;
        this.email = email;
        this.account = account;
        this.success = false;
    }
    /**
     * This method deletes an account from a user's list of accounts.
     */
    public void execute() {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                Iterator<Account> iterator = user.getAccounts().iterator();
                while (iterator.hasNext()) {
                    Account account = iterator.next();
                    if (account.getIBAN().equals(this.account) && account.getBalance() == 0) {
                        iterator.remove();
                        success = true;
                        break;
                    }
                }
            }
        }
    }
}
