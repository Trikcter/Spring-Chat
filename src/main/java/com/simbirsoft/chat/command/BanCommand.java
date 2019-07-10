package com.simbirsoft.chat.command;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class BanCommand implements BasicCommand {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public GenericRs executeCommand(CommandAttribute command, String username) {
        if(!("user".equals(command.getCommands()[0]))){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound",new Object[0],new Locale("ru"))});
        }

        if(!("Admin".equals(username))){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.notSuperuser",new Object[0],new Locale("ru"))});
        }

        Optional<User> banUser = userService.getByUsername(command.getCommands()[2]);

        if(!(banUser.isPresent())){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.userNotFound",new Object[0],new Locale("ru"))});
        }

        User user = banUser.get();

        if(!(user.getActive())){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.alreadyBan",new Object[0],new Locale("ru"))});
        }

        userService.blockUser(user.getId());

        return new GenericRs("Ban", new String[]{"Пользователь был забанен!"});
    }
}
