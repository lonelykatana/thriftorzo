package com.binar.kelompok3.secondhand.model.request.wishlist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistRequest {

    private String productId;
    private Integer userId;
}
