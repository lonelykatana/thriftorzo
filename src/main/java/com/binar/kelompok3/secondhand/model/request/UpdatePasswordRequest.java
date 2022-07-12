package com.binar.kelompok3.secondhand.model.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdatePasswordRequest {

    @NotBlank
    String password;
}
