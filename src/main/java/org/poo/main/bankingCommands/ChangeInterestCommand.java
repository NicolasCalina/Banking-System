package org.poo.main.bankingCommands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.Transaction.Transactions;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
/**
 * Command to change the interest rate of the bank
 */
public class ChangeInterestCommand implements Command {
    private ArrayList<User> users;
    private String account;
    private double interestRate;
    private int timestamp;
    public ObjectNode outputNode;

    public ChangeInterestCommand(final ArrayList<User> users,final CommandInput commandInput,
                                 final ObjectNode outputNode){
        this.users = users;
        this.account = commandInput.getAccount();
        this.interestRate = commandInput.getInterestRate();
        this.timestamp = commandInput.getTimestamp();
        this.outputNode = outputNode;
    }

    public void execute(){
        for ( User user : users ){
            for (Account account : user.getAccounts() ){
                if ( account.getIBAN().equals(this.account )){
                    if ( account.getType().equals("savings") ){
                        account.setInterestRate(interestRate);
                    } else {
                        outputNode.put("command" , "changeInterestRate");
                        ObjectNode data = outputNode.putObject("output");
                        data.put("description", "This is not a savings account");
                        data.put("timestamp", timestamp);
                        outputNode.put("timestamp", timestamp);
                    }
                }
            }
        }
    }
}
