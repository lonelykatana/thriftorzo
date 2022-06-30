package com.binar.kelompok3.secondhand.model.response.product;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponse {
    Integer id;
    String name;
    Double price;
    String description;
    String category;
    Integer status;
    Integer userId;
    List<String> imgUrl;

    public ProductResponse() {
    }


    public ProductResponse(Products products) {
        this.userId = products.getUserId().getId();
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

    public ProductResponse(Integer userId, Integer id, String name, Double price, String description, String category, Integer status, List<String> imgUrl) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.status = status;
        this.imgUrl = imgUrl;
    }

    public void mappingUserIdAndProduct(Users users) {
        this.userId = users.getId();
    }


}
