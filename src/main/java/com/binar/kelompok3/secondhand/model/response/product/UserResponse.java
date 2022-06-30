package com.binar.kelompok3.secondhand.model.response.product;

import lombok.Data;


@Data
public class UserResponse {
    Integer userId;
    String name;
    String cityName;
    String imgUrl;

    public UserResponse(Integer id, String name, String cityName, String imgUrl) {
        this.userId = id;
        this.name = name;
        this.cityName = cityName;
        this.imgUrl = imgUrl;
    }
}