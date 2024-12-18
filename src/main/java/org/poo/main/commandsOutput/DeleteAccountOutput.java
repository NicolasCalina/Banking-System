package org.poo.main.commandsOutput;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.Command;
import org.poo.main.bankingCommands.DeleteAccountCommand;
/**
 * Class that handles the output of the deleteAccount command
 */
public class DeleteAccountOutput {
    /**
     * Method that handles the output of the deleteAccount command
     * @param outputNode the output node
     * @param deleteAccount the deleteAccount command
     * @param timestamp the timestamp of the command
     */
    public static void deleteAccountOutputHandler(final ObjectNode outputNode,
                                                  final Command deleteAccount,
                                                  final int timestamp) {
        ObjectNode outputDetails = outputNode.objectNode();
        outputNode.put("command", "deleteAccount");
        if (((DeleteAccountCommand) deleteAccount).isSuccess()) {
            outputDetails.put("success", "Account deleted");
        } else {
            outputDetails.put("error",
                    "Account couldn't be deleted - see org.poo.transactions for details");
        }
        outputDetails.put("timestamp", timestamp);

        outputNode.set("output", outputDetails);
        outputNode.put("timestamp", timestamp);
    }
}
