package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.ExchangeGraph;
import org.poo.main.Transaction.SplitPaymentTransaction;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class SplitPaymentCommand implements Command {
    private ArrayList<User> users;
    private ArrayList<String> accounts;
    private double amount;
    private String currency;
    private int timestamp;
    private ExchangeGraph exchangeGraph;

    public SplitPaymentCommand(CommandInput commandInput, ArrayList<User> users, ExchangeGraph exchangeGraph) {
        this.users = users;
        this.exchangeGraph = exchangeGraph;
        this.accounts = new ArrayList<>(commandInput.getAccounts());
        this.amount = commandInput.getAmount();
        this.currency = commandInput.getCurrency();
        this.timestamp = commandInput.getTimestamp();
    }

    public void execute(){
        double split = amount / accounts.size();
        for ( String account : accounts ) {
            int hasMoney = 0;
            for ( User user : users ) {
                for ( Account acc : user.getAccounts() ) {
                    if ( acc.getIBAN().equals(account) ) {
                        if ( acc.getBalance() >= split ) {
                            hasMoney = 1;
                        }
                        break;
                    }
                }
            }
            if ( hasMoney == 0 ) {
                return;
            }
        }
        for ( String account : accounts ) {
            for ( User user : users ) {
                for ( Account acc : user.getAccounts() ) {
                    if ( acc.getIBAN().equals(account) ) {
                        if ( !acc.getCurrency().equals(currency) ) {
                            double convert = exchangeGraph.findExchangeRate(currency, acc.getCurrency());
                            acc.setBalance(acc.getBalance() - split * convert);
                        } else {
                            acc.setBalance(acc.getBalance() - split);
                        }
                        String formatted = String.format("%.2f", amount);
                        SplitPaymentTransaction splitPayment = new SplitPaymentTransaction(currency, split, accounts,"Split payment of " + formatted + " " + currency ,timestamp);
                        user.getTransactions().add(splitPayment);
                    }
                }
            }
        }
    }

}
