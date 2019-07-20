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
public class ConnectCommand implements BasicCommand {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RoomService roomService;

    @Override
    public GenericRs executeCommand(CommandAttribute command, User user) {
        if (!("room".equals(command.getCommands()[0]))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound", new Object[0], Locale.getDefault())});
        }

        User owner = user;

        if (command.getCommands().length > 3 && "".equals(command.getCommands()[2])) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.undefinedName", new Object[0], Locale.getDefault())});
        }

        Optional<Room> cnRoom = roomService.getRoomByName(command.getCommands()[2]);

        if (!(cnRoom.isPresent())) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.nonExistName", new Object[0], Locale.getDefault())});
        }

        Room room = cnRoom.get();

        if (room.getLocked() && !(room.getOwner().getId().equals(owner.getId())) && !(owner.getRoles().contains(Role.ADMIN))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.notOwner", new Object[0], Locale.getDefault())});
        }

        roomService.addParticipants(owner, room);

        return new GenericRs("Connect", new String[]{room.getName()});
    }

    @Override
    public String getName() {
        return "connect";
    }
}
