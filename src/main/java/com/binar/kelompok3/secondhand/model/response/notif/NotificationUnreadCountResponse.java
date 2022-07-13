package com.binar.kelompok3.secondhand.model.response.notif;

import com.binar.kelompok3.secondhand.model.entity.Users;
import lombok.Data;

@Data
public class NotificationUnreadCountResponse {
    private Integer userId;
    private String name;
    private String email;
    private Integer unread;

    public NotificationUnreadCountResponse(Users users, Integer unread) {
        this.userId = users.getId();
        this.name = users.getName();
        this.email = users.getEmail();
        this.unread = unread;
    }
}
