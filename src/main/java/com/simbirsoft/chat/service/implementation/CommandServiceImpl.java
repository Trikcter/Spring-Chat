package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.command.*;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandServiceImpl implements CommandService {

    private Map<String, BasicCommand> cmdMap() {
        Map<String, BasicCommand> commandMap = new HashMap<>();

        commandMap.put("rename", renameCommand);
        commandMap.put("ban", banCommand);
        commandMap.put("moderator", superUserCommand);
        commandMap.put("connect", connectCommand);
        commandMap.put("create", createCommand);
        commandMap.put("disconnect", disconnectCommand);
        commandMap.put("remove", removeCommand);

        return commandMap;
    }

    @Autowired
    private RenameCommand renameCommand;
    @Autowired
    private BanCommand banCommand;
    @Autowired
    private SuperUserCommand superUserCommand;
    @Autowired
    private CreateCommand createCommand;
    @Autowired
    private DisconnectCommand disconnectCommand;
    @Autowired
    private RemoveCommand removeCommand;
    @Autowired
    private ConnectCommand connectCommand;

    private Map<String, BasicCommand> commandMap = new HashMap<>();

    @Override
    public GenericRs executeCommand(String command, String username) {
        String[] attr = command.split(" ");
        commandMap = cmdMap();

        CommandAttribute commandAttribute = new CommandAttribute(attr);

        BasicCommand execute = commandMap.get(attr[1]);

        GenericRs genericRs = execute.executeCommand(commandAttribute, username);

        return genericRs;
    }
}
