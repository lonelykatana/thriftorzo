package com.binar.kelompok3.secondhand.model.response.notif;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.response.user.UserResponse;
import lombok.Data;

@Data
public class StatusTransactionResponse {

    private UserResponse userResponse;
    private String productId;
    private Boolean status;

    public StatusTransactionResponse(Users users, String productId) {
        this.userResponse = new UserResponse(users);
        this.productId = productId;
    }

}
