package com.simbirsoft.chat.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date dateTo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User bannedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public User getBannedUser() {
        return bannedUser;
    }

    public void setBannedUser(User bannedUser) {
        this.bannedUser = bannedUser;
    }
}
