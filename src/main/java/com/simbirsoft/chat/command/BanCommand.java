package com.simbirsoft.chat.command;

import com.simbirsoft.chat.entity.Role;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.entity.UserBan;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.service.UserBanService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Component
public class BanCommand implements BasicCommand {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserBanService userBanService;

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

        if (!(bannedUser.getEnable())) {
            return new GenericRs("Error", new String[]{messageSource.getMessage("error.alreadyBan", new Object[0], Locale.getDefault())});
        }

        if (command.getCommands().length > 4 && "-m".equals(command.getCommands()[3]) && !("".equals(command.getCommands()[4]))) {
            long timeToBan = Integer.parseInt(command.getCommands()[4]);
            timeToBan = timeToBan * 60000;

            Date now = new Date();
            Date to = new Date(now.getTime() + timeToBan);

            UserBan banTask = new UserBan();
            banTask.setDateTo(to);
            banTask.setBannedUser(bannedUser);

            userBanService.addBan(banTask);
        }

        if (command.getCommands().length == 4 && "-l".equals(command.getCommands()[3])) {
            userService.deleteFromAllRooms(bannedUser);
        }

        userService.blockUser(bannedUser.getId());

        return new GenericRs("Ok", new String[]{"success.userBan"});
    }

    @Override
    public String getName() {
        return "ban";
    }
}
