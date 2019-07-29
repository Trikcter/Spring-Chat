package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Bolgov
 * Repository для сущности Message
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    /**
     * Поиск по id сообшения
     * @param id - хранимый в базе индентификатор
     * @return - Optional для дальнейших действий
     */
    Optional<Message> findById(Long id);
}
