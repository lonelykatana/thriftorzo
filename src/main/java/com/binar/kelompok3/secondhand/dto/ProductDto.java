package com.binar.kelompok3.secondhand.dto;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.model.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;
    private String name;
    private double price;
    private Integer status;
    private String description;
    private String category;
    private Integer userId;
    private List<String> url;

    public ProductDto(Products products) {
        this.setId(products.getId());
        this.setName(products.getName());
        this.setPrice(products.getPrice());
        this.setStatus(products.getStatus());
        this.setDescription(products.getDescription());
        this.setCategory(products.getCategory());
        this.setUserId(products.getUserId().getId());
        this.url = products.getImageProducts().stream().map(imageProduct -> imageProduct.getUrl()).collect(
                Collectors.toList());
    }
}

