package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.service.imageproduct.IImageProductService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PublicController {

    private IProductsService iProductsService;
    private IImageProductService iImageProductService;
    private IUsersService iUsersService;

    @GetMapping("/get-product/{productId}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable("productId") String id) {
        Products products = iProductsService.findProductsById(id);
        ProductResponse response = new ProductResponse(products, products.getUserId());
        if (response.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<MessageResponse> getAllProductsPaginated(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        try {
            Page<Products> products = iProductsService.getAllProductsPaginated(PageRequest.of(page, size));
            return iProductsService.getMessageResponse(page, size, products);
        } catch (Exception e) {
            return new ResponseEntity(new MessageResponse("Data Kosong!"), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/get-all-products-ready")
    public ResponseEntity<MessageResponse> getAllProductReadyPaginated(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        try {
            Page<Products> products = iProductsService.getAllProductPublishPaginated(PageRequest.of(page, size, Sort.by("created_on").descending()));
            return iProductsService.getMessageResponse(page, size, products);
        } catch (Exception e) {
            return new ResponseEntity(new MessageResponse("Data Kosong!"), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/filter-category")
    public ResponseEntity<MessageResponse> filterProductByCategoryPaginated(
            @RequestParam(value = "category", defaultValue = "", required = false) String category,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        try {
            Page<Products> products =
                    iProductsService.filterProductByCategoryPaginated(category, PageRequest.of(page, size));
            return iProductsService.getMessageResponse(page, size, products);
        } catch (Exception e) {
            return new ResponseEntity(new MessageResponse("Data Kosong!"), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<MessageResponse> searchProductByNamePaginated(
            @RequestParam(value = "productName", defaultValue = "", required = false) String productName,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {

        try {
            Page<Products> products = iProductsService.searchProductByNamePaginated(productName, PageRequest.of(page, size));
            return iProductsService.getMessageResponse(page, size, products);
        } catch (Exception e) {
            return new ResponseEntity(new MessageResponse("Data Kosong!"), HttpStatus.NO_CONTENT);
        }
    }


    // COBAA
    @GetMapping("/get-all-product-search-filter-paginated")
    public ResponseEntity<MessageResponse> allAPI(
            @RequestParam(value = "productName", defaultValue = "", required = false) String name,
            @RequestParam(value = "category", defaultValue = "", required = false) String category,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        try {
            Page<Products> products = iProductsService.findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category, PageRequest.of(page, size/*, Sort.by("created_on").descending()*/));
            return iProductsService.getMessageResponse(page, size, products);
        } catch (Exception e) {
            return new ResponseEntity(new MessageResponse("Data Kosong!"), HttpStatus.NO_CONTENT);
        }
    }

}
