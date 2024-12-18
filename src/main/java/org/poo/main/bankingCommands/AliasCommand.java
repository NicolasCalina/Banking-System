package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class AliasCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String alias;
    private String account;

    public AliasCommand(final ArrayList<User> users, final String email,
                        final String alias, final String account) {
        this.users = users;
        this.email = email;
        this.alias = alias;
        this.account = account;
    }
    /**
     * This method sets the alias of the account with the given IBAN.
     */
    public void execute() {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(this.account)) {
                        account.setAlias(alias);
                    }
                }
            }
        }
    }
}
