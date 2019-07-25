package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.UserBan;

import java.util.List;

public interface UserBanService {
    void addBan(UserBan userBan);

    List<UserBan> getAll();

    void deleteTask(UserBan userBan);
}
