package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.dto.ProductDto;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.entity.Wishlist;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import com.binar.kelompok3.secondhand.service.wishlist.IWishlistService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/wishlist")
@AllArgsConstructor
public class WishlistController {

    private IWishlistService iWishlistService;
    private IUsersService iUsersService;
    private IProductsService iProductsService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("userId") Integer userId) {
        List<Wishlist> body = iWishlistService.readWishList(userId);
        List<ProductDto> products = new ArrayList<>();
        for (Wishlist wishlist : body) {
            products.add(iProductsService.getDtoFromProduct(wishlist.getProductId()));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addWishList(@RequestParam("productId") Integer productId,
                                              @RequestParam("userId") Integer userId) {
        Users users = iUsersService.findUsersById(userId);
        Products products = iProductsService.findProductsById(productId);
        Wishlist wishlist = new Wishlist(users, products);
        iWishlistService.createWishList(wishlist);
        return new ResponseEntity<>("Sukses menambah wishlist", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWishlist(@RequestParam("productId") Integer productId,
                                                 @RequestParam("userId") Integer userId) {
        Products products = iProductsService.findProductsById(productId);
        iWishlistService.deleteWishlistByProductIdAndUserId(productId, userId);
        return new ResponseEntity<>("Sukses menghapus : " + products.getName() + " dari wishlist",
                HttpStatus.ACCEPTED);
    }
}
