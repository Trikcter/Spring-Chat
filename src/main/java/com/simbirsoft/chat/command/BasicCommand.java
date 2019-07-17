package com.simbirsoft.chat.command;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;

public interface BasicCommand {
    GenericRs executeCommand(CommandAttribute command, User user);
    String getName();
}
