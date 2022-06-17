package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.Products;
import com.binar.kelompok3.secondhand.model.Users;
import com.binar.kelompok3.secondhand.model.Wishlist;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import com.binar.kelompok3.secondhand.service.wishlist.IWishlistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class WishlistController {

    private IWishlistService iWishlistService;
    private IUsersService iUsersService;
    private IProductsService iProductsService;

    public List<Products> getWishList(Integer userId) {
        List<Wishlist> body = iWishlistService.readWishList(userId);
        List<Products> products = new ArrayList<>();
        for (Wishlist wishlist : body) {
            products.add((wishlist.getProductId()));
        }
        return products;
    }

    public String addWishList(Integer productId, Integer userId) {
        Users users = iUsersService.findUsersById(userId);
        Products products = iProductsService.findProductsById(productId);
        Wishlist wishlist = new Wishlist(users,products);
        iWishlistService.createWishList(wishlist);
        return "sukses menambah wishlist";
    }
}
