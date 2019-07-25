package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.DAO.UserBanRepository;
import com.simbirsoft.chat.entity.UserBan;
import com.simbirsoft.chat.service.UserBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBanServiceImpl implements UserBanService {
    @Autowired
    private UserBanRepository userBanRepository;

    @Override
    public void addBan(UserBan userBan) {
        userBanRepository.save(userBan);
    }

    @Override
    public List<UserBan> getAll() {
        return userBanRepository.findAll();
    }

    @Override
    public void deleteTask(UserBan userBan) {
        userBanRepository.delete(userBan);
    }
}
