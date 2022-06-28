package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.dto.IImageAndProductDto;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.request.ProductRequest;
import com.binar.kelompok3.secondhand.service.imageproduct.IImageProductService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductsController {

    private IProductsService iProductsService;
    private IImageProductService iImageProductService;

    @GetMapping("/get-product/{productId}")
    public ResponseEntity<Products> findProducts(@PathVariable("productId") Integer id) {
        Products product = iProductsService.findProductsById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LinkedHashMap<String, Object> jsonResponseSpec = iProductsService.modifyJsonResponse("get", id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = iProductsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add-product/{userId}")
    public ResponseEntity<HttpStatus> addProducts(@PathVariable("userId") Integer userId,
                                                  @Valid @RequestBody ProductRequest request) {
        iProductsService.saveProducts(request.getName(), request.getPrice(), request.getStatus(),
                request.getDescription(), request.getCategory(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<HttpStatus> updateProducts(@PathVariable("id") Integer id,
                                                     @Valid @RequestBody ProductRequest request) {
        iProductsService.updateProducts(request.getName(), request.getPrice(), request.getStatus(),
                request.getDescription(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<HttpStatus> deleteProducts(@PathVariable("productId") Integer id) {
        iProductsService.deleteProductsById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



    @GetMapping("/get-product-and-image/{id}")
    public ResponseEntity<List<IImageAndProductDto>> getProductsAndImage(@PathVariable("id") Integer id) {
        List<IImageAndProductDto> productDtos = iProductsService.getProductsAndImage(id);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }



}
