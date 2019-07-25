package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.DAO.RoomBanRepository;
import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.RoomBan;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.RoomBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoomBanServiceImpl implements RoomBanService {
    @Autowired
    private RoomBanRepository roomBanRepository;

    @Override
    public void addBan(User userBan, Room roomBan, Date to) {
        RoomBan ban = new RoomBan();
        ban.setBannedUser(userBan);
        ban.setBannedRoom(roomBan);
        ban.setTo(to);

        roomBanRepository.save(ban);
    }

    @Override
    public void delete(RoomBan ban) {
        roomBanRepository.delete(ban);
    }

    @Override
    public List<RoomBan> getAll() {
        return roomBanRepository.findAll();
    }
}
