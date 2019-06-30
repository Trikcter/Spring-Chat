package com.simbirsoft.chat.service;

import com.simbirsoft.chat.DAO.MessageRepository;
import com.simbirsoft.chat.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        Message msg = messageRepository.save(message);
        return msg;
    }

    @Override
    public void delete(Long id) {
       Message message = messageRepository.findById(id).orElse(new Message());
        messageRepository.delete(message);
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = messageRepository.findAll();
        return messages;
    }
}
