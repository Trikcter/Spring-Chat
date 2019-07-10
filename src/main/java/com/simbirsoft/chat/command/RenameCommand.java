package com.simbirsoft.chat.command;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RenameCommand implements BasicCommand {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public GenericRs executeCommand(CommandAttribute command, String username) {
        if (!("user".equals(command.getCommands()[0]))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound", new Object[0], new Locale("ru"))});
        }

        String[] commands = command.getCommands();

        User user = userService.editUser(username, commands[2]);

        if (user == null) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.internalError", new Object[0], new Locale("ru"))});
        }

        return new GenericRs("Rename", new String[]{commands[2]});
    }
}
