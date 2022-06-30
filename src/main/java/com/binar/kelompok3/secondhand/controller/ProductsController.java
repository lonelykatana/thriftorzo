package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.dto.ProductDto;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.request.ProductRequest;
import com.binar.kelompok3.secondhand.service.imageproduct.IImageProductService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    // >>>> GET PRODUCTS
    @GetMapping("/get-product/{productId}")
    public ResponseEntity<ProductDto> findProducts(@PathVariable("productId") String id) {
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
    public ResponseEntity<Page<Products>> getAllProductsPaginated(@RequestParam("page") int page,
                                                                  @RequestParam("size") int size) {
        Page<Products> products = iProductsService.getAllProductsPaginated(PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    // >>>> ADD PRODUCT
    @PostMapping("/add-product")
    public ResponseEntity<HttpStatus> addProducts(@ModelAttribute ProductRequest request, @RequestParam
            MultipartFile[] imageFiles, @RequestParam("userId") Integer userId) {
        List<String> urls = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String productId = uuid.toString();
        Arrays.stream(imageFiles)
                .forEach(imageFile -> urls.add(iImageProductService.uploadFileProduct(imageFile)));

        iProductsService.saveProducts(productId, request.getName(), request.getPrice(),
                request.getStatus(),
                request.getDescription(), request.getCategory(), userId);

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
    public ResponseEntity<HttpStatus> updateProducts(@ModelAttribute ProductRequest request, @RequestParam
            MultipartFile[] imageFiles, @RequestParam("productId") String productId) {
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
        iProductsService.updateProducts(request.getName(), request.getPrice(), request.getStatus(),
                request.getDescription(), productId);
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
