package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.entity.Wishlist;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import com.binar.kelompok3.secondhand.service.wishlist.IWishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/wishlist")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WishlistController {

    private IWishlistService iWishlistService;
    private IUsersService iUsersService;
    private IProductsService iProductsService;

    @GetMapping("/get-all/{userId}")
    public ResponseEntity<List<ProductResponse>> getAllWishList(@PathVariable("userId") Integer userId) {
        List<Wishlist> body = iWishlistService.readWishList(userId);
        List<ProductResponse> products = new ArrayList<>();
        for (Wishlist wishlist : body) {
            Products product = wishlist.getProductId();
            ProductResponse productResponse = new ProductResponse(product, product.getUserId());
            products.add(productResponse);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addWishList(@RequestParam("productId") String productId,
                                              @RequestParam("userId") Integer userId) {
        Users users = iUsersService.findUsersById(userId);
        Products products = iProductsService.findProductsById(productId);
        Wishlist wishlist = new Wishlist(users, products);
        iWishlistService.createWishList(wishlist);
        return new ResponseEntity<>("Sukses menambah wishlist", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWishlist(@RequestParam("productId") String productId,
                                                 @RequestParam("userId") Integer userId) {
        Products products = iProductsService.findProductsById(productId);
        iWishlistService.deleteWishlistByProductIdAndUserId(productId, userId);
        return new ResponseEntity<>("Sukses menghapus : " + products.getName() + " dari wishlist",
                HttpStatus.ACCEPTED);
    }
}
