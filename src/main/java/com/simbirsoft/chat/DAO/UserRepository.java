package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.entity.UserBan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Bolgov
 * Repository для работы с сущностью User
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);
}
