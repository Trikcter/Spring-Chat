package com.simbirsoft.chat.model;

public class CommandAttribute {
    String[] commands;

    public CommandAttribute(String[] commands) {
        this.commands = commands;
    }

    public CommandAttribute() {
    }

    public String[] getCommands() {
        return commands;
    }

    public void setCommands(String[] commands) {
        this.commands = commands;
    }
}
