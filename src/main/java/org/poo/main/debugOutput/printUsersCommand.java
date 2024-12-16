package org.poo.main.debugOutput;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.main.Command;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class printUsersCommand implements Command {
    private ArrayList<User> users;
    private ObjectNode outputNode;
    private int timestamp;

    public printUsersCommand(ArrayList<User> users, ObjectNode outputNode, int timestamp) {
        this.users = users;
        this.outputNode = outputNode;
        this.timestamp = timestamp;
    }

    public void execute() {
        outputNode.putPOJO("command", "printUsers");
        ArrayList<User> copyUsers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            copyUsers.add(new User(users.get(i)));
        }
        outputNode.putPOJO("output", copyUsers);
        outputNode.putPOJO("timestamp", timestamp);
    }
}
