package org.poo.main.bankingCommands;

import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.User;

import java.util.ArrayList;

public class SetMinBalance implements Command {
    private ArrayList<User> users;
    private String account;
    private double amount;

    public SetMinBalance(final ArrayList<User> users, final String account,
                         final double amount) {
        this.users = users;
        this.account = account;
        this.amount = amount;
    }
    /**
     * Set the minimum balance of an account.
     */
    public void execute() {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(this.account)) {
                    account.setMinBalance(this.amount);
                    return;
                }
            }
        }
    }
}
