package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.dto.ProductDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductsController {

    private IProductsService iProductsService;
    private IImageProductService iImageProductService;
    private IUsersService iUsersService;

    // >>>> GET PRODUCTS
    @GetMapping("/get-product/{productId}")
    public ResponseEntity<ProductDto> findProducts(@PathVariable("productId") String id) {
        ProductDto product = iProductsService.getDtoFromProduct(iProductsService.findProductsById(id));
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /*@GetMapping("/get-all-products")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = iProductsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }*/

    /*@GetMapping("/get-all-products-paginated")
    public ResponseEntity<Page<Products>> getAllProductsPaginated(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Products> products = iProductsService.getAllProductsPaginated(PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }*/

    @GetMapping("get-all-products-paginated")
    public ResponseEntity getAllProductsPaginatedTest(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<Products> products = iProductsService.getAllProductsPaginated(PageRequest.of(page, size));

            List<ProductResponse> productResponses = products.stream()
                    .map(product -> new ProductResponse(product, product.getUserId()))
                    .collect(Collectors.toList());
            if (products.hasContent()) {
                ProductResponsePage productResponsePage = new ProductResponsePage(products.getTotalPages(),
                        products.getTotalElements(), page, products.isFirst(), products.isLast(), products.getSize(), productResponses);
                return new ResponseEntity(productResponsePage, HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("569", "Data Kosong!"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(null, "Data Tidak Ditemukan!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // >>>> ADD PRODUCT
    @PostMapping("/add-product")
    public ResponseEntity<HttpStatus> addProducts(@RequestParam MultipartFile[] imageFiles,
                                                  @RequestParam("userId") Integer userId,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("price") Double price,
                                                  @RequestParam("status") Integer status,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("category") String category) {
        List<String> urls = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String productId = uuid.toString();
        Arrays.stream(imageFiles)
                .forEach(imageFile -> urls.add(iImageProductService.uploadFileProduct(imageFile)));

        iProductsService.saveProducts(productId, name, price, status, description, category, userId);

        Products currentProduct = iProductsService.findProductsById(productId);
        if (currentProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (String url : urls) {
                iImageProductService.saveImageProductToDb(url, currentProduct);
            }
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    // >>>> UPDATE PRODUCT
    @PutMapping("/update-product")
    public ResponseEntity<HttpStatus> updateProducts(@RequestParam MultipartFile[] imageFiles,
                                                     @RequestParam("productId") String productId,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("price") Double price,
                                                     @RequestParam("status") Integer status,
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // >>>> SEARCH BY NAME PRODUCT
    @GetMapping("/search")
    public ResponseEntity<Page<Products>> searchProductByNamePaginated(
            @RequestParam(defaultValue = "", required = false) String productName,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Page<Products> products =
                iProductsService.searchProductByNamePaginated(productName, PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // >>>> FILTER BY CATEGORY PRODUCT
    @GetMapping("/filter-category")
    public ResponseEntity<Page<Products>> filterProductByCategoryPaginated(
            @RequestParam(defaultValue = "", required = false) String category,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Page<Products> products =
                iProductsService.filterProductByCategoryPaginated(category, PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    // >>>> DELETE PRODUCT
    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<HttpStatus> deleteProducts(@PathVariable("productId") String id) {
        iProductsService.deleteProductsById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
