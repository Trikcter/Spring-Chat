package com.simbirsoft.chat.command;

import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;

public interface BasicCommand {
    GenericRs executeCommand(CommandAttribute command, String username);
}
