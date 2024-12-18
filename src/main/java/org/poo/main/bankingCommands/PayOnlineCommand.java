package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.main.*;
import org.poo.main.Transaction.Transactions;
import org.poo.main.Transaction.PayOnlineTransactions;

import java.util.ArrayList;

@Getter
@Setter
public class PayOnlineCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String cardNumber;
    private double amount;
    private String currency;
    private ExchangeGraph exchangeGraph;
    private boolean foundCard;
    private CommandInput commandInput;

    public PayOnlineCommand(final ArrayList<User> users, final CommandInput commandInput,
                            final ExchangeGraph exchangeGraph) {
        this.users = users;
        this.email = commandInput.getEmail();
        this.cardNumber = commandInput.getCardNumber();
        this.amount = commandInput.getAmount();
        this.foundCard = false;
        this.currency = commandInput.getCurrency();
        this.exchangeGraph = exchangeGraph;
        this.commandInput = commandInput;
    }
    /**
     * This method executes the PayOnlineCommand.
     */
    public void execute() {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (!account.getCurrency().equals(currency)) {
                        double exchangeRate = exchangeGraph.findExchangeRate(currency
                                , account.getCurrency());
                        amount *= exchangeRate;
                    }

                    for (Card card : account.getCards()) {
                        if (card.getCardNumber().equals(cardNumber)) {
                            foundCard = true;

                            if (account.getBalance() < amount) {
                                Transactions insufficientFundsTransaction = new
                                        Transactions("Insufficient funds",
                                        commandInput.getTimestamp());
                                user.getTransactions().add(insufficientFundsTransaction);
                                return;
                            }

                            if (card.getIsFrozen() == 1) {
                                Transactions transaction = new
                                        Transactions("The card is frozen",
                                        commandInput.getTimestamp());
                                user.getTransactions().add(transaction);
                                return;
                            }

                            if (account.getBalance() - account.getMinBalance() < amount) {
                                card.setIsFrozen(1);
                                card.setStatus("frozen");
                                Transactions freezeTransaction = new
                                        Transactions("The card is frozen",
                                        commandInput.getTimestamp());
                                user.getTransactions().add(freezeTransaction);
                                return;
                            }

                            account.setBalance(account.getBalance() - amount);
                            PayOnlineTransactions paymentTransaction = new
                                    PayOnlineTransactions(amount, commandInput.getCommerciant(),
                                    "Card payment", commandInput.getTimestamp());
                            user.getTransactions().add(paymentTransaction);

                            String commerciant = paymentTransaction.getCommerciant();
                            double transactionAmount = paymentTransaction.getAmount();
                            double transactionTimestamp = paymentTransaction.getTimestamp();

                            account.getCommerciantsMap().putIfAbsent(commerciant, new
                                    ArrayList<>());
                            account.getCommerciantsMap().get(commerciant).add(transactionAmount);
                            account.getCommerciantsMap().get(commerciant)
                                    .add(transactionTimestamp);

                            if (card.getIsOneTimeUse() == 1) {
                                account.getCards().remove(card);
                                Card newCard = new Card();
                                newCard.setIsOneTimeUse(1);
                                account.getCards().add(newCard);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

}
