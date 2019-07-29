package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.UserBan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bolgov
 * Repository для работы с сущностью UserBan
 */
public interface UserBanRepository extends JpaRepository<UserBan,Long> {
}
