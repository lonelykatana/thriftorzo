package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.request.ProductRequest;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductsController {

    private IProductsService iProductsService;

    @GetMapping("/get-product/{productId}")
    public ResponseEntity<Products> findProducts(@PathVariable("productId") Integer id) {
        Products product = iProductsService.findProductsById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = iProductsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add-product/{userId}")
    public ResponseEntity<HttpStatus> addProducts(@PathVariable("id") Integer userId,
                                                  @Valid @RequestBody ProductRequest request) {
        iProductsService.saveProducts(request.getName(), request.getPrice(), request.getStatus(), request.getDescription(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-product/{productId}")
    public ResponseEntity<HttpStatus> updateProducts(@PathVariable("id") Integer productId,
                                                     @Valid @RequestBody ProductRequest request) {
        iProductsService.updateProducts(request.getName(), request.getPrice(), request.getStatus(), request.getDescription(), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<HttpStatus> deleteProducts(@PathVariable("productId") Integer id) {
        iProductsService.deleteProductsById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
