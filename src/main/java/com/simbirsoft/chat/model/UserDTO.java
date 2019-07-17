package com.simbirsoft.chat.model;

import com.simbirsoft.chat.entity.Role;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private Boolean active;
    private List<Role> roleSet;
    private Boolean isSuperuser = false;

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

        for (Role role : roleSet) {
            if (("MODERATOR".equals(role.getAuthority())) || ("ADMIN".equals(role.getAuthority()))) {
                isSuperuser = true;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSuperuser() {
        return isSuperuser;
    }

    public void setSuperuser(Boolean superuser) {
        isSuperuser = superuser;
    }
}
