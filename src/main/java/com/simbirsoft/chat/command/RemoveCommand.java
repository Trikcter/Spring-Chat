package com.simbirsoft.chat.command;

import com.simbirsoft.chat.entity.Role;
import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.service.RoomService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class RemoveCommand implements BasicCommand {
    @Autowired
    private RoomService roomService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;

    @Override
    public GenericRs executeCommand(CommandAttribute command, String username) {
        if (!("room".equals(command.getCommands()[0]))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound", new Object[0], new Locale("ru"))});
        }

        User owner = userService.getByUsername(username).orElse(new User());

        if(command.getCommands().length > 3 && command.getCommands()[2] == ""){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.undefinedName", new Object[0], new Locale("ru"))});
        }

        Optional<Room> rmRoom = roomService.getRoomByName(command.getCommands()[2]);

        if(!(rmRoom.isPresent())){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.nonExistName", new Object[0], new Locale("ru"))});
        }

        Room room = rmRoom.get();

        if(room.getOwner().getId() != owner.getId() && !(owner.getRoles().contains(Role.ADMIN))){
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.notOwner", new Object[0], new Locale("ru"))});
        }

        roomService.delete(room);

        return new GenericRs("Ok", new String[]{"Комната была успешно удалена!"});
    }
}
