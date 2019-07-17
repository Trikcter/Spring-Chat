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
public class RenameCommand implements BasicCommand {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RoomService roomService;

    @Override
    public GenericRs executeCommand(CommandAttribute command, User user) {
        if (!("user".equals(command.getCommands()[0]) || "room".equals(command.getCommands()[0]))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound", new Object[0], Locale.getDefault())});
        }

        String[] commands = command.getCommands();

        if ("user".equals(commands[0])) {
            User userEdit = userService.editUser(user.getUsername(), commands[2]);

            if (userEdit == null) {
                return new GenericRs("Error", new String[]{messageSource.getMessage("error.internalError", new Object[0], Locale.getDefault())});
            }

            return new GenericRs("Rename", new String[]{commands[2]});
        } else {
            User owner = user;

            if (command.getCommands().length > 3 && "".equals(command.getCommands()[2])) {
                return new GenericRs("Error", new String[]{messageSource.getMessage("error.undefinedName", new Object[0], Locale.getDefault())});
            }

            Optional<Room> rmRoom = roomService.getRoomByName(command.getCommands()[3]);

            if (!(rmRoom.isPresent())) {
                return new GenericRs("Error", new String[]{messageSource.getMessage("error.nonExistName", new Object[0], Locale.getDefault())});
            }

            Room room = rmRoom.get();

            if (room.getOwner().getId() != owner.getId() && !(owner.getRoles().contains(Role.ADMIN))) {
                return new GenericRs("Error", new String[]{messageSource.getMessage("error.notOwner", new Object[0], Locale.getDefault())});
            }

            if (commands[3].equals("")) {
                return new GenericRs("Error", new String[]{messageSource.getMessage("error.nonExistName", new Object[0], Locale.getDefault())});
            }

            room.setName(commands[2]);

            roomService.edit(room);

            return new GenericRs("Ok", new String[]{"success.renameRoom"});
        }
    }

    @Override
    public String getName() {
        return "rename";
    }
}
