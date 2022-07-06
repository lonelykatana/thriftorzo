package com.binar.kelompok3.secondhand.service.wishlist;

import com.binar.kelompok3.secondhand.model.entity.Wishlist;

import java.util.List;

public interface IWishlistService {

    List<Wishlist> getAWishlist(String productId, Integer userId);

    void createWishList(Wishlist wishlist);

    List<Wishlist> readWishList(Integer userId);

    void deleteWishlistByProductIdAndUserId(String productId, Integer userId);
}
