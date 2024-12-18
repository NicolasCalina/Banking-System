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

/**
 * Represents a command to perform a split payment transaction among multiple accounts.
 */
@Getter
@Setter
public final class SplitPaymentCommand implements Command {
    private ArrayList<User> users;
    private ArrayList<String> accounts;
    private double amount;
    private String currency;
    private int timestamp;

    /**
     * The exchange graph used to calculate exchange rates between currencies.
     */
    private ExchangeGraph exchangeGraph;

    /**
     * Constructs a SplitPaymentCommand object using the provided input.
     *
     * @param commandInput  the input containing details about the command
     * @param users         the list of users involved in the command
     * @param exchangeGraph the exchange graph for currency conversion
     */
    public SplitPaymentCommand(final CommandInput commandInput, final ArrayList<User> users,
                               final ExchangeGraph exchangeGraph) {
        this.users = users;
        this.exchangeGraph = exchangeGraph;
        this.accounts = new ArrayList<>(commandInput.getAccounts());
        this.amount = commandInput.getAmount();
        this.currency = commandInput.getCurrency();
        this.timestamp = commandInput.getTimestamp();
    }

    /**
     * Executes the split payment command.
     * The command divides the total amount equally among the accounts specified.
     * If any account has insufficient funds, the operation is aborted.
     * Currency conversion is performed if the account currency differs from the payment currency.
     * A transaction is recorded for each user involved.
     */
    public void execute() {
        double split = amount / accounts.size();

        // Check if all accounts have sufficient balance for the split payment.
        for (String account : accounts) {
            int hasMoney = 0;
            for (User user : users) {
                for (Account acc : user.getAccounts()) {
                    if (acc.getIBAN().equals(account)) {
                        if (acc.getBalance() >= split) {
                            hasMoney = 1;
                        }
                        break;
                    }
                }
            }
            if (hasMoney == 0) {
                return; // Abort if any account lacks sufficient balance.
            }
        }

        // Perform the split payment and create transactions.
        for (String account : accounts) {
            for (User user : users) {
                for (Account acc : user.getAccounts()) {
                    if (acc.getIBAN().equals(account)) {
                        if (!acc.getCurrency().equals(currency)) {
                            double convert = exchangeGraph.findExchangeRate(currency,
                                    acc.getCurrency());
                            acc.setBalance(acc.getBalance() - split * convert);
                        } else {
                            acc.setBalance(acc.getBalance() - split);
                        }
                        String formatted = String.format("%.2f", amount);
                        SplitPaymentTransaction splitPayment = new SplitPaymentTransaction(
                                currency, split, accounts,
                                "Split payment of " + formatted + " " + currency, timestamp
                        );
                        user.getTransactions().add(splitPayment);
                    }
                }
            }
        }
    }
}
