package com.binar.kelompok3.secondhand.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequest {

    @NotBlank
    String name;

    String address;
    String phone;
    String cityName;
    String imgUrl;
}
