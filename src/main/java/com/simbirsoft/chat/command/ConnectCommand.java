package com.simbirsoft.chat.command;

import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;
import org.springframework.stereotype.Component;

@Component
public class ConnectCommand implements BasicCommand {
    @Override
    public GenericRs executeCommand(CommandAttribute command, String username) {
        return null;
    }
}
