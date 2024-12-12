package org.poo.main.commandsOutput;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.Command;
import org.poo.main.bankingCommands.deleteAccountCommand;

public class deleteAccountOutput {

    public static void deleteAccountOutputHandler(ObjectNode outputNode, Command deleteAccount, int timestamp){
        ObjectNode outputDetails = outputNode.objectNode();
        outputNode.put("command", "deleteAccount");
        if ( ( (deleteAccountCommand) deleteAccount ).isSuccess() ) {
            outputDetails.put("success", "Account deleted");
        } else {
            outputDetails.put("error", "Account not found");
        }
        outputDetails.put("timestamp", timestamp);

        outputNode.set("output", outputDetails);
        outputNode.put("timestamp", timestamp);
    }
}
