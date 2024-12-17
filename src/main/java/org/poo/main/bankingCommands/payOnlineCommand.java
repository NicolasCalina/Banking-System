package org.poo.main.bankingCommands;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.main.*;
import org.poo.main.Transaction.Transactions;
import org.poo.main.Transaction.payOnlineTransactions;

import java.util.ArrayList;

@Getter
@Setter
public class payOnlineCommand implements Command {
    private ArrayList<User> users;
    private String email;
    private String cardNumber;
    private double amount;
    private String currency;
    private ExchangeGraph exchangeGraph;
    boolean foundCard;
    private CommandInput commandInput;

    public payOnlineCommand(ArrayList<User> users, CommandInput commandInput, ExchangeGraph exchangeGraph) {
        this.users = users;
        this.email = commandInput.getEmail();
        this.cardNumber = commandInput.getCardNumber();
        this.amount = commandInput.getAmount();
        this.foundCard = false;
        this.currency = commandInput.getCurrency();
        this.exchangeGraph = exchangeGraph;
        this.commandInput = commandInput;
    }

    public void execute() {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (!account.getCurrency().equals(currency)) {
                        double exchangeRate = exchangeGraph.findExchangeRate(currency, account.getCurrency());
                        amount *= exchangeRate;
                    }

                    for (Card card : account.getCards()) {
                        if (card.getCardNumber().equals(cardNumber)) {
                            foundCard = true;

                            if (account.getBalance() < amount) {
                                Transactions insufficientFundsTransaction = new Transactions("Insufficient funds", commandInput.getTimestamp());
                                user.getTransactions().add(insufficientFundsTransaction);
                                return;
                            }

                            if (card.getIsFrozen() == 1) {
                                Transactions transaction = new Transactions("The card is frozen", commandInput.getTimestamp());
                                user.getTransactions().add(transaction);
                                return;
                            }

                            if (account.getBalance() - account.getMinBalance() < amount) {
                                card.setIsFrozen(1);
                                card.setStatus("frozen");
                                Transactions freezeTransaction = new Transactions("The card is frozen", commandInput.getTimestamp());
                                user.getTransactions().add(freezeTransaction);
                                return;
                            }

                            account.setBalance(account.getBalance() - amount);
                            payOnlineTransactions paymentTransaction = new payOnlineTransactions(amount, commandInput.getCommerciant(), "Card payment", commandInput.getTimestamp());
                            user.getTransactions().add(paymentTransaction);

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
