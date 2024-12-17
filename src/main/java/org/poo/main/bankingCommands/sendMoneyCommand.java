package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.ExchangeGraph;
import org.poo.main.Transaction.Transactions;
import org.poo.main.Transaction.sendMoneyTransactions;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class sendMoneyCommand implements Command {
    private ArrayList<User> users;
    private CommandInput commandInput;
    private ExchangeGraph exchangeGraph;
    private double amount;

    public sendMoneyCommand(ArrayList<User> users, CommandInput commandInput,  ExchangeGraph exchangeGraph) {
        this.users = users;
        this.commandInput = commandInput;
        this.amount = commandInput.getAmount();
        this.exchangeGraph = exchangeGraph;
    }

    public void execute() {
        Account senderAccount = null;
        Account receiverAccount = null;
        User senderUser = null;
        User receiverUser = null;
        for (User user : users) {
            if (user.getEmail().equals(commandInput.getEmail())) {
                senderUser = user;
                for (Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(commandInput.getAccount())) {
                        senderAccount = account;
                    }
                }
            }
        }
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(commandInput.getReceiver())) {
                    receiverUser = user;
                    receiverAccount = account;
                }
            }
        }
        if (senderAccount != null && receiverAccount != null) {
            if (senderAccount.getBalance() >= amount) {
                senderAccount.setBalance(senderAccount.getBalance() - amount);
                if (!senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
                    double convert = exchangeGraph.findExchangeRate(senderAccount.getCurrency(), receiverAccount.getCurrency());
                    amount = amount * convert;
                }
                receiverAccount.setBalance(receiverAccount.getBalance() + amount);
                sendMoneyTransactions transaction = new sendMoneyTransactions(commandInput.getAccount(), commandInput.getReceiver(), commandInput.getAmount() , "sent", commandInput.getTimestamp(), commandInput.getDescription(), senderAccount.getCurrency());
                senderUser.getTransactions().add(transaction);
                sendMoneyTransactions transactionReceiver = new sendMoneyTransactions(commandInput.getAccount(), commandInput.getReceiver(), commandInput.getAmount() , "sent", commandInput.getTimestamp(), commandInput.getDescription(), senderAccount.getCurrency());
                receiverUser.getTransactions().add(transactionReceiver);
            } else {
                Transactions insufficientFundsTransaction = new Transactions("Insufficient funds", commandInput.getTimestamp());
                senderUser.getTransactions().add(insufficientFundsTransaction);
            }
        }
    }
}
