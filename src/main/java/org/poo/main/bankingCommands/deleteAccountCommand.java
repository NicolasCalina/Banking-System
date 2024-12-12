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
public class deleteAccountCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String account;
    private boolean success;

    public deleteAccountCommand(ArrayList<User> users, String email, String account) {
        this.users = users;
        this.email = email;
        this.account = account;
        this.success = false;
    }

    public void execute(){
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                Iterator<Account> iterator = user.getAccounts().iterator();
                while (iterator.hasNext()) {
                    Account account = iterator.next();
                    if (account.getIBAN().equals(this.account)) {
                        iterator.remove();
                        success = true;
                        break;
                    }
                }
            }
        }
    }
}
