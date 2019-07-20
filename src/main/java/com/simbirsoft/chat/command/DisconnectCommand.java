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
import java.util.Set;

@Component
public class DisconnectCommand implements BasicCommand {
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

        if (command.getCommands().length > 3 && "".equals(command.getCommands()[2])) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.undefinedName", new Object[0], Locale.getDefault())});
        }

        Optional<Room> rmRoom = roomService.getRoomByName(command.getCommands()[2]);

        if (!(rmRoom.isPresent())) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.nonExistName", new Object[0], Locale.getDefault())});
        }

        Room room = rmRoom.get();

        Set<User> participants = room.getParticipants();

        for (User rmUser : participants) {
            if (rmUser.getId().equals(owner.getId())) {
                participants.remove(rmUser);
                break;
            }
        }

        room.setParticipants(participants);

        /*if (!(room.getOwner().getId().equals(owner.getId())) && !(owner.getRoles().contains(Role.ADMIN))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.notOwner", new Object[0], Locale.getDefault())});
        }*/

        roomService.addRoom(room);

        return new GenericRs("Ok", new String[]{messageSource.getMessage("success.disconnectRoom", new Object[0], Locale.getDefault())});
    }

    @Override
    public String getName() {
        return "disconnect";
    }
}
