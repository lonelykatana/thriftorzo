package com.binar.kelompok3.secondhand.model.response.product;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.response.user.UserResponse;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponse {

    private String id;
    private String name;
    private Double price;
    private String description;
    private String category;
    private Integer publish;
    private Integer status;
    private UserResponse userResponse;
    private List<String> imgUrl;

    public ProductResponse() {
    }


    public ProductResponse(Products products, Users user) {
        this.userResponse = new UserResponse(user);
        this.id = products.getId();
        this.name = products.getName();
        this.price = products.getPrice();
        this.publish = products.getPublish();
        this.description = products.getDescription();
        this.category = products.getCategory();
        this.status = products.getStatus();
        this.imgUrl = products.getImageProducts()
                .stream()
                .map(ImageProduct::getUrl)
                .collect(Collectors.toList());
    }
}
