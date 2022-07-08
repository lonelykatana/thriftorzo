package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.entity.Wishlist;
import com.binar.kelompok3.secondhand.model.request.wishlist.WishlistRequest;
import com.binar.kelompok3.secondhand.model.response.ErrorResponse;
import com.binar.kelompok3.secondhand.model.response.wishlist.WishlistResponse;
import com.binar.kelompok3.secondhand.model.response.wishlist.WishlistStatusResponse;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import com.binar.kelompok3.secondhand.service.wishlist.IWishlistService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @GetMapping("/get-all-by/{userId}")
    public ResponseEntity<ErrorResponse> getAllWishList(@PathVariable("userId") Integer userId,
                                                        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        List<Wishlist> body = iWishlistService.readWishList(userId);
        List<Products> products = new ArrayList<>();
        for (Wishlist wishlist : body) {
            Products product = wishlist.getProductId();
            products.add(product);
        }
        Page<Products> productResponsePage = new PageImpl<>(products);
        try {
            return iProductsService.getErrorResponseResponseEntity(page, size, productResponsePage);

        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(null, "Data Tidak Ditemukan!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-status-wishlist")
    public ResponseEntity<WishlistStatusResponse> getAWishlist(@RequestParam("productId") String productId,
                                                               @RequestParam("userId") Integer userId) {
        List<Wishlist> wishlist = iWishlistService.getAWishlist(productId, userId);

        Boolean hasil = !wishlist.isEmpty();

        WishlistStatusResponse wishlistStatusResponse = new WishlistStatusResponse(productId, userId, hasil);
        return new ResponseEntity<>(wishlistStatusResponse, HttpStatus.OK);
    }


    @PostMapping("/add-wishlist")
    public ResponseEntity<WishlistResponse> addWishList(@RequestBody WishlistRequest request) {
        Users users = iUsersService.findUsersById(request.getUserId());
        Products products = iProductsService.findProductsById(request.getProductId());
        Wishlist wishlist = new Wishlist(users, products);
        iWishlistService.createWishList(wishlist);
        WishlistResponse wishlistResponse = new WishlistResponse(request.getProductId(), request.getUserId(), "Add '" + products.getName() + "' to " + users.getName() + "'s Wishlist.");
        return new ResponseEntity<>(wishlistResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-wishlist")
    public ResponseEntity<WishlistResponse> deleteWishlist(@RequestParam("productId") String productId,
                                                           @RequestParam("userId") Integer userId) {
        Products products = iProductsService.findProductsById(productId);
        iWishlistService.deleteWishlistByProductIdAndUserId(productId, userId);
        WishlistResponse wishlistResponse = new WishlistResponse(productId, userId, "Deleted '" + products.getName() + "' to " + userId + "'s Wishlist.");
        return new ResponseEntity<>(wishlistResponse, HttpStatus.ACCEPTED);
    }
}
