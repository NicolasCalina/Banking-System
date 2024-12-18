package org.poo.main.debugOutput;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.main.Command;
import org.poo.main.User;

import java.util.ArrayList;

@Getter
@Setter
public class PrintUsersCommand implements Command {
    private final ArrayList<User> users;
    private final ObjectNode outputNode;
    private final int timestamp;

    public PrintUsersCommand(final ArrayList<User> users,
                             final ObjectNode outputNode,
                             final int timestamp) {
        this.users = users;
        this.outputNode = outputNode;
        this.timestamp = timestamp;
    }

    /**
     * Command used for printing the users when requested by the bank.
     */
    public void execute() {
        outputNode.putPOJO("command", "printUsers");
        ArrayList<User> copyUsers = new ArrayList<>();
        for (User user : users) {
            copyUsers.add(new User(user));
        }
        outputNode.putPOJO("output", copyUsers);
        outputNode.putPOJO("timestamp", timestamp);
    }
}
