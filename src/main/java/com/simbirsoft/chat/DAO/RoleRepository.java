package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
