package com.binar.kelompok3.secondhand.dto;

import com.binar.kelompok3.secondhand.model.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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

    public ProductDto(Products products) {
        this.setId(products.getId());
        this.setName(products.getName());
        this.setPrice(products.getPrice());
        this.setStatus(products.getStatus());
        this.setDescription(products.getDescription());
        this.setCategory(products.getCategory());
    }
}

