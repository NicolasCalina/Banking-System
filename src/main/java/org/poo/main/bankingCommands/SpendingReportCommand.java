package org.poo.main.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.Account;
import org.poo.main.Command;
import org.poo.main.User;

import java.util.*;

public final class SpendingReportCommand implements Command {
    private ArrayList<User> users;
    private int startTimestamp;
    private int endTimestamp;
    private String account;
    private int timestamp;
    private ObjectNode outputNode;

    public SpendingReportCommand(final ArrayList<User> users,
                                 final CommandInput commandInput, final ObjectNode outputNode) {
        this.users = users;
        this.startTimestamp = commandInput.getStartTimestamp();
        this.endTimestamp = commandInput.getEndTimestamp();
        this.account = commandInput.getAccount();
        this.timestamp = commandInput.getTimestamp();
        this.outputNode = outputNode;
    }
    /**
     * Executes the spending report command, generating a JSON report for the specified account.
     * This includes detailed information about transactions and commerciants.
     */
    @Override
    public void execute() {
        for (User user : users) {
            for (Account acc : user.getAccounts()) {
                if (acc.getIBAN().equals(this.account)) {
                    outputNode.put("command", "spendingsReport");

                    ObjectNode accountInfo = outputNode.putObject("output");
                    accountInfo.put("IBAN", acc.getIBAN());
                    accountInfo.put("balance", acc.getBalance());
                    accountInfo.put("currency", acc.getCurrency());

                    ArrayNode transactionsArray = accountInfo.putArray("transactions");
                    ArrayList<ObjectNode> transactionList = new ArrayList<>();
                    Map<String, Double> totalPerCommerciant = new HashMap<>();

                    for (Map.Entry<String, ArrayList<Double>> entry
                            : acc.getCommerciantsMap().entrySet()) {
                        String commerciant = entry.getKey();
                        ArrayList<Double> values = entry.getValue();

                        for (int i = 0; i < values.size(); i += 2) {
                            double amount = values.get(i);
                            double transactionTimestamp = values.get(i + 1);

                            if (transactionTimestamp >= this.startTimestamp
                                    && transactionTimestamp <= this.endTimestamp) {
                                ObjectNode transactionNode = JsonNodeFactory.instance.objectNode();
                                transactionNode.put("timestamp", (int) transactionTimestamp);
                                transactionNode.put("description", "Card payment");
                                transactionNode.put("amount", amount);
                                transactionNode.put("commerciant", commerciant);

                                transactionList.add(transactionNode);

                                totalPerCommerciant.put(commerciant,
                                        totalPerCommerciant.getOrDefault(commerciant,
                                                0.0) + amount);
                            }
                        }
                    }

                    transactionList.sort(Comparator.comparingInt(o -> o.get("timestamp").asInt()));

                    for (ObjectNode transactionNode : transactionList) {
                        transactionsArray.add(transactionNode);
                    }

                    ArrayNode commerciantsArray = accountInfo.putArray("commerciants");

                    List<Map.Entry<String, Double>> sortedCommerciants = new ArrayList<>(totalPerCommerciant.entrySet());
                    sortedCommerciants.sort(Comparator.comparing(Map.Entry::getKey));

                    for (Map.Entry<String, Double> entry : sortedCommerciants) {
                        ObjectNode commerciantNode = commerciantsArray.addObject();
                        commerciantNode.put("commerciant", entry.getKey());
                        commerciantNode.put("total", entry.getValue());
                    }

                    outputNode.put("timestamp", this.timestamp);
                    return;
                }
            }
        }
        outputNode.put("command", "spendingsReport");
        ObjectNode notFoundNode = outputNode.putObject("output");
        notFoundNode.put("description","Account not found" );
        notFoundNode.put("timestamp", this.timestamp);
        outputNode.put("timestamp", this.timestamp);
    }
}
