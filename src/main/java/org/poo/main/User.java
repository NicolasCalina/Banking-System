package org.poo.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.UserInput;
import org.poo.main.Transaction.Transactions;

import java.util.ArrayList;

@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Account> accounts;
    @JsonIgnore
    private ArrayList<Transactions> transactions;

    public User(final UserInput user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public User(final User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.accounts = new ArrayList<>();
        for (int i = 0; i < user.getAccounts().size(); i++) {
            this.accounts.add(new Account(user.getAccounts().get(i)));
        }
        this.transactions = new ArrayList<>();
        for (Transactions transaction : user.getTransactions()) {
            this.transactions.add(transaction.copyTransaction());
        }
    }

    public void addAccount(final Account account) {
        accounts.add(account);
    }

}
