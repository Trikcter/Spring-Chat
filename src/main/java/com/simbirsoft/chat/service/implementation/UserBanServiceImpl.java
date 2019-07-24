package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.DAO.UserBanRepository;
import com.simbirsoft.chat.entity.UserBan;
import com.simbirsoft.chat.service.UserBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserBanServiceImpl implements UserBanService {
    @Autowired
    private UserBanRepository userBanRepository;

    @Override
    public UserBan addCondition(Boolean enable, Date dateFrom, Date dateTo) {
        return null;
    }

    @Override
    public UserBan addCondition(Boolean enable) {
        UserBan condition = new UserBan();
        condition.setEnable(enable);

        return userBanRepository.save(condition);
    }

    @Override
    public UserBan editCondition() {
        return null;
    }
}
