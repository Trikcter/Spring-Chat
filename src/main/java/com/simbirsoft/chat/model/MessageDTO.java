package com.simbirsoft.chat.model;

import com.simbirsoft.chat.entity.Message;

import java.util.List;

public class MessageDTO {
    private Long id;
    private String message;
    private String from;
    private String typeOfMessage;
    private String to;
    private List<Message> historyOfMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTypeOfMessage() {
        return typeOfMessage;
    }

    public void setTypeOfMessage(String typeOfMessage) {
        this.typeOfMessage = typeOfMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Message> getHistoryOfMessage() {
        return historyOfMessage;
    }

    public void setHistoryOfMessage(List<Message> historyOfMessage) {
        this.historyOfMessage = historyOfMessage;
    }
}
