package org.poo.main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.main.bankingCommands.*;
import org.poo.main.commandsOutput.DeleteAccountOutput;
import org.poo.main.commandsOutput.PayOnlineOutput;
import org.poo.main.debugOutput.PrintTransactionCommand;
import org.poo.main.debugOutput.PrintUsersCommand;
import org.poo.utils.Utils;

import java.util.ArrayList;
@Getter
@Setter
public final class Bank {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<ExchangeRates> exchangeRates = new ArrayList<>();
    private ArrayList<Commerciants> commerciants = new ArrayList<>();
    private ExchangeGraph exchangeGraph = new ExchangeGraph();

    public Bank(final ObjectInput inputData) {
        for (int i = 0; i < inputData.getUsers().length; i++) {
            User user = new User(inputData.getUsers()[i]);
            users.add(user);
        }
        for (int i = 0; i < inputData.getExchangeRates().length; i++) {
            ExchangeRates exchangeRate = new ExchangeRates(inputData.getExchangeRates()[i]);
            exchangeGraph.addExchangeRate(exchangeRate.getFrom(), exchangeRate.getTo(),
                    exchangeRate.getRate());
        }
    }
    /**
     * Executes the commands from the input and adds the output to the output array
     * @param input the input data
     * @param output the output array
     */
    public void doActions(final ObjectInput input,
                          final  ArrayNode output) {
        Utils.resetRandom();

        for (CommandInput commandInput : input.getCommands()) {
            ObjectNode outputNode = output.objectNode();
            switch (commandInput.getCommand()) {
                case "printUsers":
                    Command printUsers = new PrintUsersCommand(users, outputNode,
                            commandInput.getTimestamp());
                    CommandInvoker invokerPrintUsers = new CommandInvoker(printUsers);
                    invokerPrintUsers.executeCommand();
                    break;
                case "printTransactions":
                    Command printTransactions = new PrintTransactionCommand(users, outputNode,
                            commandInput.getEmail(), commandInput.getTimestamp());
                    CommandInvoker invokerPrintTransactions = new
                            CommandInvoker(printTransactions);
                    invokerPrintTransactions.executeCommand();
                    break;
                case "addAccount":
                    Account newAccount = new Account(commandInput);
                    Command addAccount = new AddAccountCommand(users,
                            commandInput.getEmail(),
                            newAccount, commandInput.getTimestamp());
                    CommandInvoker invoker = new CommandInvoker(addAccount);
                    invoker.executeCommand();
                    break;
                case "createCard":
                    Command createCard = new CreateCardCommand(users, commandInput.getEmail(),
                            commandInput.getAccount(), 0, commandInput);
                    CommandInvoker invokerCreateCard = new CommandInvoker(createCard);
                    invokerCreateCard.executeCommand();
                    break;
                case "createOneTimeCard":
                    Command createOneTimeCard = new CreateCardCommand(users,
                            commandInput.getEmail(), commandInput.getAccount(), 1, commandInput);
                    CommandInvoker invokerCreateOneTimeCard = new CommandInvoker(createOneTimeCard);
                    invokerCreateOneTimeCard.executeCommand();
                    break;
                case "addFunds":
                    Command addFunds = new AddFundsCommand(users, commandInput.getAccount(),
                            commandInput.getAmount());
                    CommandInvoker invokerAddFunds = new CommandInvoker(addFunds);
                    invokerAddFunds.executeCommand();
                    break;
                case "payOnline":
                    Command payOnline = new PayOnlineCommand(users,
                            commandInput,
                            exchangeGraph);
                    CommandInvoker invokerPayOnline = new CommandInvoker(payOnline);
                    invokerPayOnline.executeCommand();
                    PayOnlineOutput.payOnlineOutputHandler(payOnline, outputNode,
                            commandInput.getTimestamp());
                    break;
                case "deleteAccount":
                    Command deleteAccount = new DeleteAccountCommand(users,
                            commandInput.getEmail(),
                            commandInput.getAccount());
                    CommandInvoker invokerDeleteAccount = new CommandInvoker(deleteAccount);
                    invokerDeleteAccount.executeCommand();
                    DeleteAccountOutput.deleteAccountOutputHandler(outputNode, deleteAccount,
                            commandInput.getTimestamp());
                    break;
                case "deleteCard":
                    Command deleteCard = new DeleteCardCommand(users, commandInput.getEmail(),
                            commandInput.getCardNumber(), commandInput.getTimestamp());
                    CommandInvoker invokerDeleteCard = new CommandInvoker(deleteCard);
                    invokerDeleteCard.executeCommand();
                    break;
                case "sendMoney":
                    Command sendMoney = new SendMoneyCommand(users, commandInput,
                            exchangeGraph);
                    CommandInvoker invokerSendMoney = new CommandInvoker(sendMoney);
                    invokerSendMoney.executeCommand();
                    break;
                case "setAlias":
                    Command setAlias = new AliasCommand(users, commandInput.getEmail(),
                            commandInput.getAlias(), commandInput.getAccount());
                    CommandInvoker invokerSetAlias = new CommandInvoker(setAlias);
                    invokerSetAlias.executeCommand();
                    break;
                case "setMinimumBalance":
                    Command setMinimumBalance = new SetMinBalance(users,
                            commandInput.getAccount(),
                            commandInput.getAmount());
                    CommandInvoker invokerSetMinBalance = new CommandInvoker(setMinimumBalance);
                    invokerSetMinBalance.executeCommand();
                    break;
                case "checkCardStatus":
                    Command checkCardStatus = new CheckCardStatus(users, commandInput,
                            outputNode);
                    CommandInvoker invokerCheckCardStatus = new CommandInvoker(checkCardStatus);
                    invokerCheckCardStatus.executeCommand();
                    break;
                case "splitPayment":
                    Command splitPayment = new SplitPaymentCommand(commandInput, users,
                            exchangeGraph);
                    CommandInvoker invokerSplitPayment = new CommandInvoker(splitPayment);
                    invokerSplitPayment.executeCommand();
                    break;
                case "report":
                    Command report = new ReportCommand(users, commandInput, outputNode);
                    CommandInvoker invokerReport = new CommandInvoker(report);
                    invokerReport.executeCommand();
                    break;
                case "spendingsReport":
                    Command spendingsReport = new SpendingReportCommand(users, commandInput,
                            outputNode);
                    CommandInvoker invokerSpendingsReport = new CommandInvoker(spendingsReport);
                    invokerSpendingsReport.executeCommand();
                    break;
                default:
                    break;
            }
            if (!outputNode.isEmpty()) {
                output.add(outputNode);
            }
        }
    }


}
