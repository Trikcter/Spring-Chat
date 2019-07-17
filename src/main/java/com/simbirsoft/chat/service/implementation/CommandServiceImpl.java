package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.command.*;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommandServiceImpl implements CommandService {

    @PostConstruct
    private void cmdMap() {
        commandMap.put(renameCommand.getName(), renameCommand);
        commandMap.put(banCommand.getName(), banCommand);
        commandMap.put(superUserCommand.getName(), superUserCommand);
        commandMap.put(connectCommand.getName(), connectCommand);
        commandMap.put(createCommand.getName(), createCommand);
        commandMap.put(disconnectCommand.getName(), disconnectCommand);
        commandMap.put(removeCommand.getName(), removeCommand);
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
    public GenericRs executeCommand(String command, User user) {
        String[] attr = command.split(" ");

        CommandAttribute commandAttribute = new CommandAttribute(attr);

        BasicCommand execute = commandMap.get(attr[1]);

        GenericRs genericRs = execute.executeCommand(commandAttribute, user);

        return genericRs;
    }
}
