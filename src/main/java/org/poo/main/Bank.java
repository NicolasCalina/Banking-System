package org.poo.main;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.utils.Utils;

import java.util.ArrayList;
@Getter
@Setter
public class Bank {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<ExchangeRates> exchangeRates = new ArrayList<>();

    public Bank(ObjectInput inputData){
        for (int i = 0; i < inputData.getUsers().length; i++) {
            User user = new User(inputData.getUsers()[i]);
            users.add(user);
        }
        for ( int i = 0 ; i < inputData.getExchangeRates().length; i++){
            ExchangeRates exchangeRate = new ExchangeRates(inputData.getExchangeRates()[i]);
            exchangeRates.add(exchangeRate);
        }
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
                    for ( User user : users ){
                        if ( user.getEmail().equals(commandInput.getEmail() ) ){
                            Account newAcount = new Account(commandInput);
                            user.addAccount(newAcount);
                        }
                    }
                    break;
                case "createCard":
                    for ( User user : users ){
                        if ( user.getEmail().equals(commandInput.getEmail() ) ){
                            for ( Account account : user.getAccounts() ){
                                if ( account.getIBAN().equals(commandInput.getAccount()) ){
                                    Card newCard = new Card();
                                    account.getCards().add(newCard);
                                }
                            }
                        }
                    }
                    break;
                case "addFunds":
                     for ( User user : users ){
                            for ( Account account : user.getAccounts() ){
                                if ( account.getIBAN().equals(commandInput.getAccount()) ){
                                    account.setBalance(account.getBalance() + commandInput.getAmount());
                                }
                            }
                    }
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
            if ( !outputNode.isEmpty() ){
                output.add(outputNode);
            }
        }
    }


}
