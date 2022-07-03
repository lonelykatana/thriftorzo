package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.service.imageproduct.IImageProductService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductsController {

    private IProductsService iProductsService;
    private IImageProductService iImageProductService;
    private IUsersService iUsersService;

    // >>>> GET PRODUCTS
    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") String productId) {
        Products product = iProductsService.findProductsById(productId);
        ProductResponse productResponse = new ProductResponse(product, product.getUserId());
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    // >>>> ADD PRODUCT
    @PostMapping("/add-product")
    public ResponseEntity<Products> addProducts(@RequestParam MultipartFile[] imageFiles,
                                                @RequestParam("userId") Integer userId,
                                                @RequestParam("name") String name,
                                                @RequestParam("price") Double price,
                                                @RequestParam("status") Integer status,
                                                @RequestParam("publish") Integer publish,
                                                @RequestParam("description") String description,
                                                @RequestParam("category") String category) {
        List<String> urls = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String productId = uuid.toString();
        Arrays.stream(imageFiles)
                .forEach(imageFile -> urls.add(iImageProductService.uploadFileProduct(imageFile)));

        iProductsService.saveProducts(productId, name, price, status, publish, description, category, userId);

        Products currentProduct = iProductsService.findProductsById(productId);
        if (currentProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (String url : urls) {
                iImageProductService.saveImageProductToDb(url, currentProduct);
            }
        }

        Products products = iProductsService.findProductsById(productId);
        return new ResponseEntity<>(products, HttpStatus.CREATED);
    }

    // >>>> UPDATE PRODUCT
    @PutMapping("/update-product")
    public ResponseEntity<Products> updateProducts(@RequestParam MultipartFile[] imageFiles,
                                                   @RequestParam("productId") String productId,
                                                   @RequestParam("name") String name,
                                                   @RequestParam("price") Double price,
                                                   @RequestParam("status") Integer status,
                                                   @RequestParam("publish") Integer publish,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("category") String category) {
        List<String> urls = new ArrayList<>();
        Arrays.stream(imageFiles)
                .forEach(imageFile -> urls.add(iImageProductService.uploadFileProduct(imageFile)));

        Products currentProduct = iProductsService.findProductsById(productId);
        if (currentProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (String url : urls) {
                iImageProductService.saveImageProductToDb(url, currentProduct);
            }
        }
        iProductsService.updateProducts(name, price, status,
                description, category, productId);

        Products updatedProduct = iProductsService.findProductsById(productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // >>>> DELETE PRODUCT
    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<HttpStatus> deleteProducts(@PathVariable("productId") String id) {
        iProductsService.deleteProductsById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
