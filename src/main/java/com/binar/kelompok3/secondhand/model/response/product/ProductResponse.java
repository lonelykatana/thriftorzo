package com.binar.kelompok3.secondhand.model.response.product;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.model.entity.Products;
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
}
