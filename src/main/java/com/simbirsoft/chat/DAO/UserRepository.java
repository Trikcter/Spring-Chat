package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
}
