package org.poo.main.debugOutput;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.Command;
import org.poo.main.Transaction.sendMoneyTransactions;
import org.poo.main.User;

import java.util.ArrayList;

public class printTransactionCommand implements Command {
    private ArrayList<User> users;
    private ObjectNode outputNode;
    private String email;
    private int timestamp;

    public printTransactionCommand(ArrayList<User> users, ObjectNode outputNode, String email, int timestamp) {
        this.users = users;
        this.outputNode = outputNode;
        this.email = email;
        this.timestamp = timestamp;
    }

    public void execute(){
        for ( User user : users ){
            if ( user.getEmail().equals(email) ){
                outputNode.put("command", "printTransactions");
                User copyUser = new User(user);
                outputNode.putPOJO("output", copyUser.getTransactions());
                break;
            }
        }
        outputNode.putPOJO("timestamp", timestamp);
    }
}
