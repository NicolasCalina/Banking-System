package org.poo.main;

import lombok.Getter;

import java.util.Queue;

public class CommandInvoker {
    Command command;

    public CommandInvoker(Command command) {
        this.command = command;
    }


    public void executeCommand() {
            command.execute();
    }

}
