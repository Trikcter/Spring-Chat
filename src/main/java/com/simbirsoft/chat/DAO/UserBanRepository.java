package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.UserBan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBanRepository extends JpaRepository<UserBan,Long> {
}
