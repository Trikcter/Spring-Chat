package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.RoomBan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bolgov
 *
 * Repository для работы с сущностью RoomBan
 */
public interface RoomBanRepository extends JpaRepository<RoomBan, Long> {
}
