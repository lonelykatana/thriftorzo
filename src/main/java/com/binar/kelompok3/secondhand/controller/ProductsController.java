package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.dto.ProductDto;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.request.ProductRequest;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponsePage;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductsController {

    private IProductsService iProductsService;

    // >>>> GET PRODUCTS
    @GetMapping("/get-product/{productId}")
    public ResponseEntity<ProductDto> findProducts(@PathVariable("productId") Integer id) {
        ProductDto product = iProductsService.getDtoFromProduct(iProductsService.findProductsById(id));
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = iProductsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/get-all-products-paginated")
    public ResponseEntity<Page<Products>> getAllProductsPaginated(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Products> products = iProductsService.getAllProductsPaginated(PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("get-all-products-paginated-test")
    public ResponseEntity getAllProductsPaginatedTest(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<Products> products = iProductsService.getAllProductsPaginated(PageRequest.of(page, size));

            List<ProductResponse> productResponses = products.stream()
                    .map(ProductResponse::new)
                    .collect(Collectors.toList());
            if (products.hasContent()) {
                ProductResponsePage productResponsePage = new ProductResponsePage(products.getTotalPages(),
                        products.getTotalElements(), page, products.isFirst(), products.isLast(), productResponses);
                return new ResponseEntity<>(productResponsePage, HttpStatus.OK);
            } else {
                throw new Exception("product tidak ditemukan");
            }
        } catch (Exception e) {
            return new ResponseEntity("Kambing", HttpStatus.OK);
        }
    }


    // >>>> ADD PRODUCT
    @PostMapping("/add-product/{userId}")
    public ResponseEntity<HttpStatus> addProducts(@PathVariable("userId") Integer userId,
                                                  @Valid @RequestBody ProductRequest request) {
        iProductsService.saveProducts(request.getName(), request.getPrice(), request.getStatus(),
                request.getDescription(), request.getCategory(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // >>>> UPDATE PRODUCT
    @PutMapping("/update-product/{productId}")
    public ResponseEntity<HttpStatus> updateProducts(@PathVariable("id") Integer productId,
                                                     @Valid @RequestBody ProductRequest request) {
        iProductsService.updateProducts(request.getName(), request.getPrice(), request.getStatus(),
                request.getDescription(), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // >>>> SEARCH BY NAME PRODUCT
    @GetMapping("/search")
    public ResponseEntity<Page<Products>> searchProductByNamePaginated(@RequestParam(defaultValue = "", required = false) String productName,
                                                                       @RequestParam("page") int page,
                                                                       @RequestParam("size") int size) {
        Page<Products> products = iProductsService.searchProductByNamePaginated(productName, PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // >>>> FILTER BY CATEGORY PRODUCT
    @GetMapping("/filter-category")
    public ResponseEntity<Page<Products>> filterProductByCategoryPaginated(@RequestParam(defaultValue = "", required = false) String category,
                                                                           @RequestParam("page") int page,
                                                                           @RequestParam("size") int size) {
        Page<Products> products = iProductsService.filterProductByCategoryPaginated(category, PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    // >>>> DELETE PRODUCT
    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<HttpStatus> deleteProducts(@PathVariable("productId") Integer id) {
        iProductsService.deleteProductsById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
