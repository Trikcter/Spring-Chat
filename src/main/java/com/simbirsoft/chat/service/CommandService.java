package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.GenericRs;

public interface CommandService {
    GenericRs executeCommand(String command, User user);
}
