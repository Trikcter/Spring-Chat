package com.simbirsoft.chat.model;

import com.simbirsoft.chat.entity.Role;

import java.util.List;

public class UserDTO {
    private String username;
    private Boolean active;
    private List<Role> roleSet;
    private Boolean isGod = false;

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

    public List<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(List<Role> roleSet) {
        this.roleSet = roleSet;

        for(Role role:roleSet){
            if((role.getAuthority() == "MODERATOR") | (role.getAuthority() == "ADMIN")){
                isGod = true;
            }
        }
    }

    public Boolean getGod() {
        return isGod;
    }

    public void setGod(Boolean god) {
        isGod = god;
    }
}
