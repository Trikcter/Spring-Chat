package com.simbirsoft.chat.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_disconnect")
public class RoomBan {
    @Id
    @GeneratedValue
    Long id;

    @Column
    Date dateTo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User bannedUser;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room bannedRoom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTo() {
        return dateTo;
    }

    public void setTo(Date to) {
        this.dateTo = to;
    }

    public User getBannedUser() {
        return bannedUser;
    }

    public void setBannedUser(User bannedUser) {
        this.bannedUser = bannedUser;
    }

    public Room getBannedRoom() {
        return bannedRoom;
    }

    public void setBannedRoom(Room bannedRoom) {
        this.bannedRoom = bannedRoom;
    }
}
