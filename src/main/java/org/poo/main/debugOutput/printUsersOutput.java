package org.poo.main.debugOutput;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.User;

import java.util.ArrayList;

public class printUsersOutput {

    public static void printUsersOutputHandler(ObjectNode outputNode, ArrayList<User> users, int timestamp){
        outputNode.putPOJO("command" , "printUsers");
        ArrayList<User> copyUsers = new ArrayList<>();
        for ( int i = 0 ; i < users.size(); i++ ){
            copyUsers.add(new User(users.get(i)));
        }
        outputNode.putPOJO("output" , copyUsers);
        outputNode.putPOJO("timestamp" , timestamp);
    }
}
