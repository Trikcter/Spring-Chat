package com.simbirsoft.chat.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Users {
    @Id
    @GeneratedValue
    private Long Id;

    @Column
    private String name;

    @Column
    private String password;

    @ManyToMany
    private Set<Role> roles;

    public Users() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
