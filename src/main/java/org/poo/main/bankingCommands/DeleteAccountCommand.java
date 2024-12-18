package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.Transaction.Transactions;
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
    private int timestamp;

    public DeleteAccountCommand(final ArrayList<User> users,
                                final String email, final String account,
                                final int timestamp) {
        this.users = users;
        this.email = email;
        this.account = account;
        this.success = false;
        this.timestamp = timestamp;
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
                        return;
                    } else if (account.getBalance() > 0) {
                        Transactions transactions = new Transactions("Account couldn't be deleted - there are funds remaining", timestamp);
                        user.getTransactions().add(transactions);
                        return;
                    }
                }
            }
        }
    }
}
