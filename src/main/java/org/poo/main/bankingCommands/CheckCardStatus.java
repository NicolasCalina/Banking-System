package org.poo.main.bankingCommands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.main.Account;
import org.poo.main.Card;
import org.poo.main.Command;
import org.poo.main.Transaction.Transactions;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class CheckCardStatus implements Command {
    private ArrayList<User> users;
    private String account;
    private String cardNumber;
    private int timestamp;
    ObjectNode outputNode;

    public CheckCardStatus(ArrayList<User> users, CommandInput commandInput, ObjectNode outputNode){
        this.users = users;
        this.account = commandInput.getAccount();
        this.cardNumber = commandInput.getCardNumber();
        this.timestamp = commandInput.getTimestamp();
        this.outputNode = outputNode;
    }

    public void execute(){
        int isFound = 0;
        for ( User user : users ){
            for (Account account : user.getAccounts() ){
                for (Card  card : account.getCards() ){
                    if ( card.getCardNumber().equals(cardNumber) ){
                        isFound = 1;
                        if ( account.getBalance() == 0 || account.getBalance() == account.getMinBalance()  ){
                            Transactions transaction = new Transactions("You have reached the minimum amount of funds, the card will be frozen", timestamp);
                            user.getTransactions().add(transaction);
                            return;
                        }
                    }
                }
            }
        }
        if (isFound == 0) {
            outputNode.put("command", "checkCardStatus");
            ObjectNode outputDetails = outputNode.putObject("output");
            outputDetails.put("description", "Card not found");
            outputDetails.put("timestamp", timestamp);
            outputNode.put("timestamp", timestamp);
        }
    }
}
