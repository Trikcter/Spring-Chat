package com.simbirsoft.chat.command;

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

@Component
public class CreateCommand implements BasicCommand {
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

        if (command.getCommands().length > 3 && command.getCommands()[2] == "-c") {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.undefinedName", new Object[0], new Locale("ru"))});
        }

        Room room = new Room();

        room.setName(command.getCommands()[2]);
        room.setOwner(owner);

        if (command.getCommands().length == 4 && command.getCommands()[3] == "-c") {
            room.setLocked(true);
        }

        room.setLocked(false);

        roomService.addRoom(room);

        return new GenericRs("Ok", new String[]{"Комната была успешно создана!"});
    }
}
