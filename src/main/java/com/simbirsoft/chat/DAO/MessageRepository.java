package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
