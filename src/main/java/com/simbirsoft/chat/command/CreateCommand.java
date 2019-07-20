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
import java.util.Optional;

@Component
public class CreateCommand implements BasicCommand {
    @Autowired
    private RoomService roomService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;

    @Override
    public GenericRs executeCommand(CommandAttribute command, User user) {
        if (!("room".equals(command.getCommands()[0]))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound", new Object[0], Locale.getDefault())});
        }

        User owner = user;

        if (command.getCommands().length > 3 && "-c".equals(command.getCommands()[2])) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.undefinedName", new Object[0], Locale.getDefault())});
        }

        Optional<Room> findRoom = roomService.getRoomByName(command.getCommands()[2]);

        if (findRoom.isPresent()) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.existRoom", new Object[0], Locale.getDefault())});
        }

        Room room = new Room();

        room.setName(command.getCommands()[2]);
        room.setOwner(owner);

        if (command.getCommands().length == 4 && "-c".equals(command.getCommands()[3])) {
            room.setLocked(true);
        }

        room.setLocked(false);

        roomService.addRoom(room);

        return new GenericRs("Create", new String[]{room.getName()});
    }

    @Override
    public String getName() {
        return "create";
    }
}
