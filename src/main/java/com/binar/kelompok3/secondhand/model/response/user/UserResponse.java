package com.binar.kelompok3.secondhand.model.response.user;

import com.binar.kelompok3.secondhand.model.entity.Users;
import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String cityName;
    private String imgUrl;

    public UserResponse(Users users) {
        this.id = users.getId();
        this.name = users.getName();
        this.email = users.getEmail();
        this.phone = users.getPhone();
        this.address = users.getAddress();
        this.cityName = users.getCityName();
        this.imgUrl = users.getImgUrl();
    }
}
