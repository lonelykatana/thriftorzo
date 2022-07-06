package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.response.ErrorResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponsePage;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ProductsServiceImpl implements IProductsService {

    private ProductsRepository productsRepository;
    private IUsersService iUsersService;

    @Override
    public void saveProducts(String id, String name, Double price, Integer status, Integer publish,
                             String description,
                             String category, Integer userId) {
        Products products = new Products();
        products.setId(id);
        products.setName(name);
        products.setPrice(price);
        products.setStatus(status);
        products.setPublish(publish);
        products.setDescription(description);
        products.setCategory(category);
        Users users = iUsersService.findUsersById(userId);
        products.setUserId(users);
        productsRepository.save(products);
    }


    @Override
    public void updateProducts(String name, Double price, Integer status, Integer publish, String description,
                               String category, String id) {
        productsRepository.updateProducts(name, price, status, publish, description, category, id);
    }

    @Override
    public Page<Products> getAllProductsPaginated(Pageable pageable) {
        return productsRepository.findAllByOrderByCreatedOnDesc(pageable);
    }

    @Override
    public Page<Products> getAllProductPublishPaginated(Pageable pageable) {
        return productsRepository.getAllProductReadyPaginated(pageable);
    }

    @Override
    public Page<Products> getAllSoldProductsPaginated(Integer userId, Pageable pageable) {
        return productsRepository.getAllProductSoldPaginated(userId, pageable);
    }

    @Override
    public Page<Products> searchProductByNamePaginated(String productName, Pageable pageable) {
        List<Products> products = productsRepository.findProductsByNameContainingIgnoreCase(productName, pageable);
        List<Products> publishedProducts = products.stream()
                .filter(val -> val.getPublish().equals(1))
                .collect(Collectors.toList());
        return new PageImpl<>(publishedProducts);
    }

    @Override
    public Page<Products> filterProductByCategoryPaginated(String category, Pageable pageable) {
        List<Products> products = productsRepository.findProductsByCategoryContainingIgnoreCase(category, pageable);
        List<Products> publishedProducts = products.stream()
                .filter(val -> val.getPublish().equals(1))
                .collect(Collectors.toList());
        return new PageImpl<>(publishedProducts);
    }

    @Override
    public void deleteProductsById(String id) {
        productsRepository.deleteProductsById(id);
    }

    @Override
    public Products findProductsById(String id) {
        return productsRepository.findProductsById(id);
    }

    @Override
    public void updateStatus(String id, Integer status) {
        Products products = findProductsById(id);
        products.setStatus(status);
        productsRepository.save(products);
    }

    @Override
    public ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(Integer page, Integer size, Page<Products> products) {
        List<ProductResponse> productResponses = products.stream()
                .map(product -> new ProductResponse(product, product.getUserId()))
                .collect(Collectors.toList());
        if (products.hasContent()) {
            ProductResponsePage productResponsePage = new ProductResponsePage(products.getTotalPages(),
                    products.getTotalElements(), page, products.isFirst(), products.isLast(),
                    size/*products.getSize()*/, productResponses);
            return new ResponseEntity(productResponsePage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("569", "Data Kosong!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
