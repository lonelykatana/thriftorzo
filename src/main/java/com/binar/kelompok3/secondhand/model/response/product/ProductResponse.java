package com.binar.kelompok3.secondhand.model.response.product;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponse {

    IUsersService usersService;

    String id;
    String name;
    Double price;
    String description;
    String category;
    Integer status;
    UserResponse userResponse;
    List<String> imgUrl;

    public ProductResponse() {
    }


    public ProductResponse(Products products, Users user) {
        this.userResponse = userMapper(user);
        this.id = products.getId();
        this.name = products.getName();
        this.price = products.getPrice();
        this.description = products.getDescription();
        this.category = products.getCategory();
        this.status = products.getStatus();
        this.imgUrl = products.getImageProducts()
                .stream()
                .map(ImageProduct::getUrl)
                .collect(Collectors.toList());
    }

    private UserResponse userMapper(Users user) {
        return new UserResponse(user.getId(), user.getName(), user.getCityName(), user.getImgUrl());
    }
}
