package com.simbirsoft.chat.Task;

import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.RoomBan;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.entity.UserBan;
import com.simbirsoft.chat.service.RoomBanService;
import com.simbirsoft.chat.service.RoomService;
import com.simbirsoft.chat.service.UserBanService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class BanTask {
    @Autowired
    private UserBanService userBanService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomBanService roomBanService;

    @Scheduled(fixedRate = 60000)
    public void unbanUserTask(){
        List<UserBan> banList = userBanService.getAll();

        for(UserBan userBan : banList){
            Date now = new Date();

            if(now.after(userBan.getDateTo())){
                User user = userBan.getBannedUser();
                user.setEnable(true);

                userService.save(user);
                userBanService.deleteTask(userBan);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void unbanRoomTask(){
        List<RoomBan> disconnectList = roomBanService.getAll();

        for(RoomBan roomBan : disconnectList){
            Date now = new Date();

            if(now.after(roomBan.getTo())){
                User user = roomBan.getBannedUser();
                Room room = roomBan.getBannedRoom();

                roomService.unbanUser(user,room);
            }
        }
    }
}
