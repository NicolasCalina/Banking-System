package org.poo.main;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * CommandInvoker class
 */
public final class CommandInvoker {
    private Command command;

    public CommandInvoker(final Command command) {
        this.command = command;
    }

    /**
     * executeCommand method
     */
    public void executeCommand() {
        command.execute();
    }

}
