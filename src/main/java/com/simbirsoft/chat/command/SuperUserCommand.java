package com.simbirsoft.chat.command;

import com.simbirsoft.chat.entity.Role;
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
public class SuperUserCommand implements BasicCommand {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public GenericRs executeCommand(CommandAttribute command, String username) {
        if(!("user".equals(command.getCommands()[0]))){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound",new Object[0],new Locale("ru"))});
        }

        User requestUser = userService.getByUsername(username).orElse(new User());

        if(!(requestUser.getRoles().contains(Role.ADMIN))){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.notSuperuser",new Object[0],new Locale("ru"))});
        }

        Optional<User> moderatorUser = userService.getByUsername(command.getCommands()[2]);

        if(!(moderatorUser.isPresent())){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.userNotFound",new Object[0],new Locale("ru"))});
        }

        User user = moderatorUser.get();

        if(!("-n".equals(command.getCommands()[3]) || "-d".equals(command.getCommands()[3]))){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound",new Object[0],new Locale("ru"))});
        }

        if("-n".equals(command.getCommands()[3])){
            if(user.getRoles().contains(Role.MODERATOR)){
                return new GenericRs("Error", new String[]{messageSource.getMessage("error.alreadyModerator",new Object[0],new Locale("ru"))});
            }

            userService.makeModerator(user.getId());

            return new GenericRs("Ban", new String[]{"Пользователь был повышен до модератора!"});
        }
        else{
            if(!(user.getRoles().contains(Role.MODERATOR))){
                return new GenericRs("Error", new String[]{messageSource.getMessage("error.alreadyUser",new Object[0],new Locale("ru"))});
            }

            userService.removeModerator(user.getId());

            return new GenericRs("Ban", new String[]{"Пользователь был понижен!"});
        }
    }
}
