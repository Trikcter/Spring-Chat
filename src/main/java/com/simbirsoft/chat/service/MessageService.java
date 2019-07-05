package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<Message> save(Message message);

    void delete(Long id);

    List<Message> getAll();
}
