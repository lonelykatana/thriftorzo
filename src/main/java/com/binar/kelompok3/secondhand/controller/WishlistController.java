package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.entity.Wishlist;
import com.binar.kelompok3.secondhand.model.request.wishlist.WishlistAuthRequest;
import com.binar.kelompok3.secondhand.model.request.wishlist.WishlistRequest;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.binar.kelompok3.secondhand.utils.Constant.DATA_EMPTY;


@RestController
@RequestMapping("/wishlist")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WishlistController {

    private IWishlistService iWishlistService;
    private IUsersService iUsersService;
    private IProductsService iProductsService;

    @GetMapping("/get-user-wishlist")
    public ResponseEntity<MessageResponse> getAllWishListAuth(Authentication authentication,
                                                              @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                              @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Users user = iUsersService.findByEmail(authentication.getName());
        List<Wishlist> body = iWishlistService.readWishList(user.getId());
        List<Products> products = new ArrayList<>();
        for (Wishlist wishlist : body) {
            Products product = wishlist.getProductId();
            products.add(product);
        }
        Page<Products> productResponsePage = new PageImpl<>(products);
        try {
            return iProductsService.getMessageResponse(page, size, productResponsePage);

        } catch (Exception e) {
            return new ResponseEntity(new MessageResponse(DATA_EMPTY), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-status-wishlist")
    public ResponseEntity<WishlistStatusResponse> getAWishlistAuth(@RequestParam("productId") String productId,
                                                                   Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());
        Integer userId = user.getId();
        List<Wishlist> wishlist = iWishlistService.getAWishlist(productId, userId);

        Boolean hasil = !wishlist.isEmpty();

        WishlistStatusResponse wishlistStatusResponse = new WishlistStatusResponse(productId, userId, hasil);
        return new ResponseEntity<>(wishlistStatusResponse, HttpStatus.OK);
    }

    @PostMapping("/add-wishlist")
    public ResponseEntity<WishlistResponse> addWishListAuth(@RequestBody WishlistAuthRequest request,
                                                            Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());
        Integer userId = user.getId();
        Users users = iUsersService.findUsersById(userId);
        Products products = iProductsService.findProductsById(request.getProductId());
        Wishlist wishlist = new Wishlist(users, products);
        iWishlistService.createWishList(wishlist);
        WishlistResponse wishlistResponse = new WishlistResponse(request.getProductId(), userId, "Add '" + products.getName() + "' to " + users.getName() + "'s Wishlist.");
        return new ResponseEntity<>(wishlistResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-wishlist")
    public ResponseEntity<WishlistResponse> deleteWishlistAuth(@RequestParam("productId") String productId,
                                                               Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());
        Integer userId = user.getId();
        Products products = iProductsService.findProductsById(productId);
        iWishlistService.deleteWishlistByProductIdAndUserId(productId, userId);
        WishlistResponse wishlistResponse = new WishlistResponse(productId, userId, "Deleted '" + products.getName() + "' to " + userId + "'s Wishlist.");
        return new ResponseEntity<>(wishlistResponse, HttpStatus.ACCEPTED);
    }
}
