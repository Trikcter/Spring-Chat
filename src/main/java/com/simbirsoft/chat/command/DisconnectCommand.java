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

import java.util.*;

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

        if (command.getCommands().length > 4 && "-l".equals(command.getCommands()[3]) && !("".equals(command.getCommands()[4]))) {
            String banUsername = command.getCommands()[4];
            User banUser = userService.getByUsername(banUsername).orElse(new User());

            roomService.banUser(banUser,room);

            if (command.getCommands().length > 6 && "-m".equals(command.getCommands()[5]) && !("".equals(command.getCommands()[6]))){
                long timeToBan = Integer.parseInt(command.getCommands()[6]);
                timeToBan = timeToBan * 60000;

                TimerTask task = new TimerTask() {
                    public void run() {
                        roomService.unbanUser(banUser,room);
                        cancel();
                    }
                };

                Timer timer = new Timer("TimerForUnBanRoom");

                timer.scheduleAtFixedRate(task, timeToBan, 1000L);

                try {
                    Thread.sleep(1000L * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }else {
            Set<User> participants = room.getParticipants();

            for (User rmUser : participants) {
                if (rmUser.getId().equals(owner.getId())) {
                    participants.remove(rmUser);
                    break;
                }
            }

            room.setParticipants(participants);

            roomService.addRoom(room);
        }

        return new GenericRs("Ok", new String[]{messageSource.getMessage("success.disconnectRoom", new Object[0], Locale.getDefault())});
    }

    @Override
    public String getName() {
        return "disconnect";
    }
}
