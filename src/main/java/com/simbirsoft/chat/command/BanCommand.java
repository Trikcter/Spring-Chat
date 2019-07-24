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
import java.util.Timer;
import java.util.TimerTask;

@Component
public class BanCommand implements BasicCommand {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public GenericRs executeCommand(CommandAttribute command, User user) {
        if (!("user".equals(command.getCommands()[0]))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.commandNotFound", new Object[0], Locale.getDefault())});
        }

        User requestUser = user;

        if (!(requestUser.getRoles().contains(Role.MODERATOR) || requestUser.getRoles().contains(Role.ADMIN))) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.notSuperuser", new Object[0], Locale.getDefault())});
        }

        Optional<User> banUser = userService.getByUsername(command.getCommands()[2]);

        if (!(banUser.isPresent())) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.userNotFound", new Object[0], Locale.getDefault())});
        }

        User bannedUser = banUser.get();

        if (!(bannedUser.getActive().getEnable())) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.alreadyBan", new Object[0], Locale.getDefault())});
        }

        if (command.getCommands().length > 4 && "-m".equals(command.getCommands()[3]) && !("".equals(command.getCommands()[4]))) {
            BanTask(bannedUser,command);
        } else {
            if (command.getCommands().length > 4 && "-l".equals(command.getCommands()[3]) && !("".equals(command.getCommands()[4]))) {
                userService.deleteFromAllRooms(bannedUser);
            }
        }

        userService.blockUser(bannedUser.getId());

        return new GenericRs("Ok", new String[]{"success.userBan"});
    }

    @Override
    public String getName() {
        return "ban";
    }

    private void BanTask(User userBan,CommandAttribute command){
        long timeToBan = Integer.parseInt(command.getCommands()[4]);
        timeToBan = timeToBan * 60000;

        TimerTask task = new TimerTask() {
            public void run() {
                userService.save(userBan);
                cancel();
            }
        };

        Timer timer = new Timer("TimerForUnBan");

        timer.scheduleAtFixedRate(task, timeToBan, 1000L);

        try {
            Thread.sleep(1000L * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
