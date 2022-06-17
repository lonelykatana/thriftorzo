package com.binar.kelompok3.secondhand.service.wishlist;

import com.binar.kelompok3.secondhand.model.Wishlist;

import java.util.List;

public interface IWishlistService {

    void createWishList(Wishlist wishlist);

    List<Wishlist> readWishList(Integer userId);

    void deleteById(Integer id);
}
