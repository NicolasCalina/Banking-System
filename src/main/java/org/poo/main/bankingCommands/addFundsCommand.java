package org.poo.main.bankingCommands;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.User;

@Getter
@Setter
public class addFundsCommand implements Command {
    private ArrayList<User> users;
    private String account;
    private double amount;

    public addFundsCommand(ArrayList<User> users, String account, double amount) {
        this.users = users;
        this.account = account;
        this.amount = amount;
    }

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
