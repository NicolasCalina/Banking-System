package org.poo.main.commandsOutput;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.Command;
import org.poo.main.bankingCommands.PayOnlineCommand;
/**
 * PayOnlineOutput class is responsible for handling the output of the PayOnlineCommand.
 * It checks if the card is found and if not, it creates the output accordingly.
 */
public class PayOnlineOutput {
    /**
     * payOnlineOutputHandler method is responsible for handling the output of the PayOnlineCommand.
     * It checks if the card is found and if not, it creates the output accordingly.
     * @param payOnline PayOnlineCommand object
     * @param outputNode ObjectNode object
     * @param timestamp int
     */
    public static void payOnlineOutputHandler(final Command payOnline, final ObjectNode outputNode,
                                              final int timestamp) {
        if (!((PayOnlineCommand) payOnline).isFoundCard()) {
            outputNode.putPOJO("command", "payOnline");
            ObjectNode payOnlineDetails = outputNode.objectNode();
            payOnlineDetails.put("timestamp", timestamp);
            payOnlineDetails.put("description", "Card not found");
            outputNode.set("output", payOnlineDetails);
            outputNode.put("timestamp", timestamp);
        }
    }
}
