package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.dto.ProductDto;
import com.binar.kelompok3.secondhand.model.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;

public interface IProductsService {

    void saveProducts(String id, String name, Double price, Integer status, String description,
                      String category, Integer userId);

    void updateProducts(String name, Double price, Integer status, String description,
                        String category, String id);

    List<Products> getAllProducts();

    Page<Products> getAllProductsPaginated(Pageable pageable);

    Page<Products> searchProductByNamePaginated(String name, Pageable pageable);

    Page<Products> filterProductByCategoryPaginated(String category, Pageable pageable);

    void deleteProductsById(String id);

    Products findProductsById(String id);

    LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String id);

    ProductDto getDtoFromProduct(Products products);

}
