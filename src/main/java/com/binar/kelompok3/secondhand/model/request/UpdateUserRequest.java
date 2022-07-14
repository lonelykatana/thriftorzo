package com.binar.kelompok3.secondhand.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequest {
    String name;
    String address;
    String phone;
    String cityName;
    String imgUrl;
}
