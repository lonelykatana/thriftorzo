package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.response.product.ErrorResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {

    private IProductsService iProductsService;
    private IImageProductService iImageProductService;
    private IUsersService iUsersService;

    @GetMapping("/get-product/{productId}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable("productId") String id) {
        Products products = iProductsService.findProductsById(id);
        ProductResponse response = new ProductResponse(products, products.getUserId());
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<ErrorResponse> getAllProductsPaginatedTest(
            @RequestParam(value = "page", defaultValue = "0",
                    required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        try {
            Page<Products> products = iProductsService.getAllProductsPaginated(PageRequest.of(page, size));

            return getErrorResponseResponseEntity(page, products);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(null, "Data Tidak Ditemukan!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all-products-ready")
    public ResponseEntity<ErrorResponse> getAllProductReadyPaginated(
            @RequestParam(value = "page", defaultValue =
                    "0",
                    required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        try {
            Page<Products> products =
                    iProductsService.getAllProductReadyPaginated(PageRequest.of(page, size));

            return getErrorResponseResponseEntity(page, products);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(null, "Data Tidak Ditemukan!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/filter-category")
    public ResponseEntity<Page<Products>> filterProductByCategoryPaginated(
            @RequestParam(defaultValue = "", required = false) String category,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Page<Products> products =
                iProductsService.filterProductByCategoryPaginated(category, PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Products>> searchProductByNamePaginated(
            @RequestParam(defaultValue = "", required = false) String productName,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Page<Products> products =
                iProductsService.searchProductByNamePaginated(productName, PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(
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
            return new ResponseEntity<>(new ErrorResponse("569", "Data Kosong!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
