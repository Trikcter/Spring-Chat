package com.simbirsoft.chat.entity;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String name;
    private User owner;

    private Set<User> participants = new HashSet<>();

    public Room(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Room() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }
}
