package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.ExchangeGraph;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class sendMoneyCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String Sender;
    private String Receiver;
    private double amount;
    private ExchangeGraph exchangeGraph;

    public sendMoneyCommand(ArrayList<User> users, String email, String Sender, String Receiver, double amount, ExchangeGraph exchangeGraph) {
        this.users = users;
        this.email = email;
        this.Sender = Sender;
        this.Receiver = Receiver;
        this.amount = amount;
        this.exchangeGraph = exchangeGraph;
    }

    public void execute(){
        Account senderAccount = null;
        Account receiverAccount = null;
        for (User user : users ){
            if ( user.getEmail().equals(email) ){
                for (Account account : user.getAccounts() ){
                        if (account.getIBAN().equals(Sender)) {
                            senderAccount = account;
                        }
                }
            }
        }
        for (User user : users ){
                for (Account account : user.getAccounts() ){
                    if (account.getIBAN().equals(Receiver)) {
                        receiverAccount = account;
                    }
                }
        }
        if (senderAccount != null && receiverAccount != null){
            if (senderAccount.getBalance() >= amount){
                senderAccount.setBalance(senderAccount.getBalance() - amount);
                if ( !senderAccount.getCurrency().equals(receiverAccount.getCurrency() ) ){
                    double convert = exchangeGraph.findExchangeRate(senderAccount.getCurrency(), receiverAccount.getCurrency());
                    amount = amount * convert;
                }
                receiverAccount.setBalance(receiverAccount.getBalance() + amount);
            }
        }
    }
}
