package com.simbirsoft.chat.command;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;
import org.springframework.stereotype.Component;

@Component
public class DisconnectCommand implements BasicCommand {
    @Override
    public GenericRs executeCommand(CommandAttribute command, User user) {
        return null;
    }

    @Override
    public String getName() {
        return "disconnect";
    }
}
