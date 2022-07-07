package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.response.ErrorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IProductsService {

    void saveProducts(String id, String name, Double price, Integer status, Integer publish, String description,
                      String category, Integer userId);

    void updateProducts(String name, Double price, Integer status, Integer publish, String description,
                        String category, String id);

    Page<Products> getAllProductsPaginated(Pageable pageable);

    Page<Products> getAllProductPublishPaginated(Pageable pageable); // ini

    Page<Products> getAllSoldProductsPaginated(Integer userId, Pageable pageable);

    Page<Products> getProductsByUserId(Integer userId, Pageable pageable);

    Page<Products> searchProductByNamePaginated(String name, Pageable pageable); // ini

    Page<Products> findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category, Pageable pageable);

    Page<Products> filterProductByCategoryPaginated(String category, Pageable pageable); // ini

    void deleteProductsById(String id);

    Products findProductsById(String id);

    void updateStatus(String id, Integer status);

    ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(Integer page, Integer size, Page<Products> products);
}
