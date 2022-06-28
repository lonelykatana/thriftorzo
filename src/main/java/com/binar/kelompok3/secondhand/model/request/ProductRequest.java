package com.binar.kelompok3.secondhand.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequest {

    @NotBlank
    String name;

    @NotBlank
    Double price;

    Integer status;
    String description;
    String category;
}
