package org.poo.main.bankingCommands;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.User;

@Getter
@Setter
public class AddFundsCommand implements Command {
    private ArrayList<User> users;
    private String account;
    private double amount;

    public AddFundsCommand(final ArrayList<User> users,
                           final String account, final double amount) {
        this.users = users;
        this.account = account;
        this.amount = amount;
    }
    /**
     * This method adds funds to the account.
     */
    public void execute() {
        for (User user : users) {
            for (Account accountUser : user.getAccounts()) {
                if (accountUser.getIBAN().equals(account)) {
                    accountUser.setBalance(accountUser.getBalance() + amount);
                }
            }
        }
    }
}
