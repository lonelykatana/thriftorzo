package com.binar.kelompok3.secondhand.model.response.wishlist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistStatusResponse {

    private String productId;
    private Integer userId;
    private Boolean wishlistStatus;
}
