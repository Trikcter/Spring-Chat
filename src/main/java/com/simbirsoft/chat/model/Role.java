package com.simbirsoft.chat.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Role {

    @Id
    @GeneratedValue
    private Long Id;

    @Column
    private String name;

    @ManyToMany
    private Set<Users> users;

    public Role() {
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

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
}
