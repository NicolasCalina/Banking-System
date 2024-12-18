package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.Transaction.Transactions;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class AddAccountCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private Account account;
    private int timestamp;

    public AddAccountCommand(final ArrayList<User> users, String email, Account account, int timestamp) {
        this.users = users;
        this.email = email;
        this.account = account;
        this.timestamp = timestamp;
    }

    public void execute() {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                user.addAccount(account);
                Transactions transaction = new Transactions("New account created" , timestamp);
                user.getTransactions().add(transaction);
                break;
            }
        }
    }

}
