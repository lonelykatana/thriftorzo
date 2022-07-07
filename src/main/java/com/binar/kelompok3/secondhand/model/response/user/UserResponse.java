package com.binar.kelompok3.secondhand.model.response.user;

import com.binar.kelompok3.secondhand.model.entity.Users;
import lombok.Data;

@Data
public class UserResponse {
    private Integer userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String imgUrl;

    public UserResponse(Users users) {
        this.userId = users.getId();
        this.name = users.getName();
        this.email = users.getEmail();
        this.phone = users.getPhone();
        this.address = users.getAddress();
        this.city = users.getCityName();
        this.imgUrl = users.getImgUrl();
    }
}
