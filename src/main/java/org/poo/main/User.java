package org.poo.main;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.UserInput;

import java.util.ArrayList;

@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Account> accounts;

    public User (UserInput user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.accounts = new ArrayList<>();
    }

    public User (User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.accounts = new ArrayList<>();
        for ( int i = 0 ; i < user.getAccounts().size() ; i++ ){
            this.accounts.add(new Account(user.getAccounts().get(i)));
        }
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

}
