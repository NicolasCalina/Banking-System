package org.poo.main;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.main.bankingCommands.*;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

@Getter
@Setter
public class Bank {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<ExchangeRates> exchangeRates = new ArrayList<>();
    private ArrayList<Commerciants> commerciants = new ArrayList<>();

    public Bank(ObjectInput inputData){
        for (int i = 0; i < inputData.getUsers().length; i++) {
            User user = new User(inputData.getUsers()[i]);
            users.add(user);
        }
        for ( int i = 0 ; i < inputData.getExchangeRates().length; i++){
            ExchangeRates exchangeRate = new ExchangeRates(inputData.getExchangeRates()[i]);
            exchangeRates.add(exchangeRate);
        }
//            for (int i = 0; i < inputData.getCommerciants().length; i++) {
//                Commerciants commerciant = new Commerciants(inputData.getCommerciants()[i]);
//                commerciants.add(commerciant);
//            }
    }

    public void doActions(ObjectInput input, ArrayNode output){
        Utils.resetRandom();

        for (CommandInput commandInput : input.getCommands() ){
            ObjectNode outputNode = output.objectNode();
            switch(commandInput.getCommand()){
                case "printUsers":
                    outputNode.putPOJO("command" , "printUsers");
                    ArrayList<User> copyUsers = new ArrayList<>();
                    for ( int i = 0 ; i < users.size(); i++ ){
                        copyUsers.add(new User(users.get(i)));
                    }
                    outputNode.putPOJO("output" , copyUsers);
                    outputNode.putPOJO("timestamp" , commandInput.getTimestamp());
                    break;
                case "addAccount":
                    Account newAccount = new Account(commandInput);
                    Command addAccount = new AddAccountCommand(users, commandInput.getEmail(), newAccount);
                    CommandInvoker invoker = new CommandInvoker(addAccount);
                    invoker.executeCommand();
                    break;
                case "createCard":
                    Command createCard = new createCardCommand(users, commandInput.getEmail(), commandInput.getAccount() , 0);
                    CommandInvoker invokerCreateCard = new CommandInvoker(createCard);
                    invokerCreateCard.executeCommand();
                    break;
                case "createOneTimeCard":
                    Command createOneTimeCard = new createCardCommand(users, commandInput.getEmail(), commandInput.getAccount() , 1);
                    CommandInvoker invokerCreateOneTimeCard = new CommandInvoker(createOneTimeCard);
                    invokerCreateOneTimeCard.executeCommand();
                    break;
                case "addFunds":
                    Command addFunds = new addFundsCommand(users, commandInput.getAccount(), commandInput.getAmount());
                    CommandInvoker invokerAddFunds = new CommandInvoker(addFunds);
                    invokerAddFunds.executeCommand();
                    break;
                case "payOnline":
                    Command payOnline = new payOnlineCommand(users, commandInput.getEmail() , commandInput.getCardNumber(), commandInput.getAmount());
                    CommandInvoker invokerPayOnline = new CommandInvoker(payOnline);
                    invokerPayOnline.executeCommand();
                    if ( !((payOnlineCommand) payOnline).isFoundCard() ) {
                        outputNode.putPOJO("command", "payOnline");
                        ObjectNode payOnlineDetails = output.objectNode();
                        payOnlineDetails.put("timestamp", commandInput.getTimestamp());
                        payOnlineDetails.put("description", "Card not found");
                        outputNode.set("output", payOnlineDetails);
                        outputNode.put("timestamp", commandInput.getTimestamp());
                    }
                    break;
                case "deleteAccount":
                    outputNode.put("command", "deleteAccount");
                    Command deleteAccount = new deleteAccountCommand(users, commandInput.getEmail(), commandInput.getAccount());
                    CommandInvoker invokerDeleteAccount = new CommandInvoker(deleteAccount);
                    invokerDeleteAccount.executeCommand();
                    ObjectNode outputDetails = output.objectNode();
                    if ( ( (deleteAccountCommand) deleteAccount ).isSuccess() ) {
                        outputDetails.put("success", "Account deleted");
                    } else {
                        outputDetails.put("error", "Account not found");
                    }
                    outputDetails.put("timestamp", commandInput.getTimestamp());

                    outputNode.set("output", outputDetails);
                    outputNode.put("timestamp", commandInput.getTimestamp());
                    break;
                case "deleteCard":
                    Command deleteCard = new deleteCardCommand(users, commandInput.getEmail(), commandInput.getCardNumber());
                    CommandInvoker invokerDeleteCard = new CommandInvoker(deleteCard);
                    invokerDeleteCard.executeCommand();
                    break;
                default:
                    break;
            }
            if ( !outputNode.isEmpty() ){
                output.add(outputNode);
            }
        }
    }


}
