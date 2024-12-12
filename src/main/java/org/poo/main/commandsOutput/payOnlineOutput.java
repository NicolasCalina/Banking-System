package org.poo.main.commandsOutput;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.Command;
import org.poo.main.bankingCommands.payOnlineCommand;

public class payOnlineOutput {

    public static void payOnlineOutputHandler(Command payOnline, ObjectNode outputNode, int timestamp) {
        if ( !((payOnlineCommand) payOnline).isFoundCard() ) {
            outputNode.putPOJO("command", "payOnline");
            ObjectNode payOnlineDetails = outputNode.objectNode();
            payOnlineDetails.put("timestamp", timestamp);
            payOnlineDetails.put("description", "Card not found");
            outputNode.set("output", payOnlineDetails);
            outputNode.put("timestamp", timestamp);
        }
    }
}
