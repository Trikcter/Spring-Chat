package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.RoomBan;
import com.simbirsoft.chat.entity.User;

import java.util.Date;
import java.util.List;

public interface RoomBanService {
    void addBan(User userBan, Room roomBan, Date to);

    void delete(RoomBan ban);

    List<RoomBan> getAll();
}
