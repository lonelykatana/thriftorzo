package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponsePage;
import com.binar.kelompok3.secondhand.service.imageproduct.IImageProductService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductsController {

    private IProductsService iProductsService;
    private IUsersService iUsersService;
    private IImageProductService iImageProductService;

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

    @GetMapping("/get-sold-products")
    public ResponseEntity<MessageResponse> getSoldProducts(Authentication authentication,
                                                           @RequestParam(value = "page", defaultValue = "0",
                                                                   required = false) int page,
                                                           @RequestParam(value = "size", defaultValue = "10",
                                                                   required = false) int size) {
        Users user = iUsersService.findByEmail(authentication.getName());
        Page<Products> products =
                iProductsService.getAllSoldProductsPaginated(user.getId(), PageRequest.of(page, size));

        return iProductsService.getMessageResponse(page, size, products);
    }

    @GetMapping("/get-products-by-userid")
    public ResponseEntity<MessageResponse> getAllProductById(Authentication authentication,
                                                             @RequestParam(value = "page", defaultValue = "0",
                                                                     required = false) int page,
                                                             @RequestParam(value = "size", defaultValue = "10",
                                                                     required = false) int size) {
        Users user = iUsersService.findByEmail(authentication.getName());
        Page<Products> products = iProductsService.getProductsByUserId(user.getId(), PageRequest.of(page, size));

        return iProductsService.getMessageResponse(page, size, products);
    }

    @GetMapping("/get-interested-products")
    public ResponseEntity<MessageResponse> getDiminati(Authentication authentication,
                                                       @RequestParam(value = "page", defaultValue =
                                                               "0", required = false) Integer page,
                                                       @RequestParam(value = "size", defaultValue =
                                                               "10", required = false) Integer size) {
        Users users = iUsersService.findByEmail(authentication.getName());
        Page<Products> products = iProductsService.getAllProductsDiminati(users.getId(),
                PageRequest.of(page, size));
        return getResponse(page, products);
    }

    static ResponseEntity<MessageResponse> getResponse(
            @RequestParam(value = "page", defaultValue = "0",
                    required = false) int page, Page<Products> products) {
        List<ProductResponse> productResponses = products.stream()
                .map(product -> new ProductResponse(product, product.getUserId()))
                .collect(Collectors.toList());
        if (products.hasContent()) {
            ProductResponsePage productResponsePage = new ProductResponsePage(products.getTotalPages(),
                    products.getTotalElements(), page, products.isFirst(), products.isLast(),
                    products.getSize(), productResponses);
            return new ResponseEntity(productResponsePage, HttpStatus.OK);
        } else {
            return new ResponseEntity(new MessageResponse("Data Kosong!"), HttpStatus.NO_CONTENT);
        }
    }

    // >>>> ADD PRODUCT
    @PostMapping("/add-product")
    public ResponseEntity<ProductResponse> addProducts(@RequestParam MultipartFile[] imageFiles,
                                                       @RequestParam("userId") Integer userId,
                                                       @RequestParam("name") String name,
                                                       @RequestParam("price") Double price,
                                                       @RequestParam(value = "status", required = false, defaultValue = "1") Integer status,
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

        Products product = iProductsService.findProductsById(productId);
        ProductResponse productResponse = new ProductResponse(product, product.getUserId());

        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @PostMapping("/add-product-test")
    public ResponseEntity<ProductResponse> addProductsAuth(@RequestParam MultipartFile[] imageFiles,
                                                           @RequestParam("name") String name,
                                                           @RequestParam("price") Double price,
                                                           @RequestParam(value = "status", required = false, defaultValue = "1") Integer status,
                                                           @RequestParam("publish") Integer publish,
                                                           @RequestParam("description") String description,
                                                           @RequestParam("category") String category,
                                                           Authentication authentication) {
        List<String> urls = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String productId = uuid.toString();
        Arrays.stream(imageFiles)
                .forEach(imageFile -> urls.add(iImageProductService.uploadFileProduct(imageFile)));

        Users user = iUsersService.findByEmail(authentication.getName());

        iProductsService.saveProducts(productId, name, price, status, publish, description, category, user.getId());

        Products currentProduct = iProductsService.findProductsById(productId);
        if (currentProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (String url : urls) {
                iImageProductService.saveImageProductToDb(url, currentProduct);
            }
        }

        Products product = iProductsService.findProductsById(productId);
        ProductResponse productResponse = new ProductResponse(product, product.getUserId());

        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    // >>>> UPDATE PRODUCT
    @PutMapping("/update-product")
    public ResponseEntity<ProductResponse> updateProducts(@RequestParam(required = false) MultipartFile[] imageFiles,
                                                          @RequestParam("productId") String productId,
                                                          @RequestParam("name") String name,
                                                          @RequestParam("price") Double price,
                                                          @RequestParam("status") Integer status,
                                                          @RequestParam("publish") Integer publish,
                                                          @RequestParam("description") String description,
                                                          @RequestParam("category") String category) {

        List<String> urls = new ArrayList<>();
        if (imageFiles == null) {
            iProductsService.updateProducts(name, price, status, publish,
                    description, category, productId);
        } else {
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
            iProductsService.updateProducts(name, price, status, publish,
                    description, category, productId);

        }
        Products updatedProduct = iProductsService.findProductsById(productId);
        ProductResponse productResponse = new ProductResponse(updatedProduct, updatedProduct.getUserId());

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    // >>>> DELETE PRODUCT
    @DeleteMapping("/delete-product")
    public ResponseEntity<HttpStatus> deleteProducts(@RequestParam("productId") String id) {
        iProductsService.deleteProductsById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-image")
    public ResponseEntity<LinkedHashMap<String, Object>> deleteImage(@RequestParam String url,
                                                                     @RequestParam String productId) {
        Products products = iProductsService.findProductsById(productId);
        ImageProduct imageProduct = iImageProductService.findImageProductByUrl(url);

        if (imageProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iImageProductService.deleteImageProduct(imageProduct, products);
        LinkedHashMap<String, Object> jsonResponse = iImageProductService.modifyJsonResponse("delete", null);

        return new ResponseEntity<>(jsonResponse, HttpStatus.ACCEPTED);
    }


}
