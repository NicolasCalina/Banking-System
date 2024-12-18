package org.poo.main.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.Transaction.Transactions;
import org.poo.main.User;

import java.util.ArrayList;

public class ReportCommand implements Command {
    private ArrayList<User> users;
    private int startTimestamp;
    private int endTimestamp;
    private String account;
    private int timestamp;
    private ObjectNode outputNode;

    public ReportCommand(final ArrayList<User> users, final CommandInput commandInput,
                         final ObjectNode outputNode) {
        this.users = users;
        this.startTimestamp = commandInput.getStartTimestamp();
        this.endTimestamp = commandInput.getEndTimestamp();
        this.account = commandInput.getAccount();
        this.timestamp = commandInput.getTimestamp();
        this.outputNode = outputNode;
    }
    /**
     * This method is used to generate the report for a specific account.
     */
    public void execute() {
        for (User user : users) {
            for (Account acc : user.getAccounts()) {
                if (acc.getIBAN().equals(this.account)) {
                    outputNode.put("command", "report");
                    ObjectNode accountInfo = outputNode.putObject("output");
                    accountInfo.put("IBAN", acc.getIBAN());
                    accountInfo.put("balance", acc.getBalance());
                    accountInfo.put("currency", acc.getCurrency());

                    ArrayNode transactionsArray = accountInfo.putArray("transactions");
                    for (Transactions transaction : user.getTransactions()) {
                        if (transaction.getTimestamp() >= startTimestamp
                                && transaction.getTimestamp() <= endTimestamp) {
                            transactionsArray.addPOJO(transaction);
                        }
                    }
                    outputNode.put("timestamp", this.timestamp);
                    return;
                }
            }
        }
        outputNode.putPOJO("command", "report");
        ObjectNode error = outputNode.putObject("output");
        error.put("description", "Account not found");
        error.put("timestamp", this.timestamp);
        outputNode.put("timestamp", this.timestamp);
    }
}
