package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.RoomBan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomBanRepository extends JpaRepository<RoomBan,Long> {
}
