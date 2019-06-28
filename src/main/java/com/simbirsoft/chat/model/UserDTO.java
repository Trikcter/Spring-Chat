package com.simbirsoft.chat.model;

import com.simbirsoft.chat.entity.Role;

import java.util.Set;

public class UserDTO {
    private String username;
    private Boolean active;
    private Set<Role> roleSet;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }
}
