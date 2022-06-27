package com.binar.kelompok3.secondhand.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank
    @Size(min = 6, max = 40)
    String password;
}
