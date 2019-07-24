package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.UserBan;

import java.util.Date;

public interface UserBanService {
    UserBan addCondition(Boolean enable, Date dateFrom, Date dateTo);

    UserBan addCondition(Boolean enable);

    UserBan editCondition();
}
