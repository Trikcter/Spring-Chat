package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.Message;

import java.util.List;

public interface MessageService {
    Message save(Message message);

    void delete(Long id);

    List<Message> getAll();
}
