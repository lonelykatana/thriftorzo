package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;

public interface IProductsService {

    void saveProducts(String id, String name, Double price, Integer status, Integer publish, String description,
                      String category, Integer userId);

    void updateProducts(String name, Double price, Integer status, Integer publish, String description,
                        String category, String id);

    Page<Products> getAllProductsPaginated(Pageable pageable);

    Page<Products> getAllProductPublishPaginated(Pageable pageable);

    Page<Products> searchProductByNamePaginated(String name, Pageable pageable);

    Page<Products> filterProductByCategoryPaginated(String category, Pageable pageable);

    void deleteProductsById(String id);

    Products findProductsById(String id);

    LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String id);
}
