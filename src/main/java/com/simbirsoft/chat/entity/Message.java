package com.simbirsoft.chat.entity;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue
    private Long Id;

    @Column
    private String username;

    @Column
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public Message() {
    }

    public String getSocketMessage() {
        return username + ":" + message;
    }
}
