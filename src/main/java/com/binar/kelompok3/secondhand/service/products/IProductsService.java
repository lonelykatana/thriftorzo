package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.dto.IImageAndProductDto;
import com.binar.kelompok3.secondhand.dto.ProductDto;
import com.binar.kelompok3.secondhand.model.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;

public interface IProductsService {

    void saveProducts(String name, Double price, Integer status, String description, String Category,
                      Integer userId);

    void updateProducts(String name, Double price, Integer status, String description, Integer id);

    List<Products> getAllProducts();

    Page<Products> getAllProductsPaginated(Pageable pageable);

    Page<Products> searchProductByNamePaginated(String name, Pageable pageable);

    Page<Products> filterProductByCategoryPaginated(String category, Pageable pageable);

    void deleteProductsById(Integer id);

    Products findProductsById(Integer id);

    List<IImageAndProductDto> getProductsAndImage(Integer id);

    LinkedHashMap<String, Object> modifyJsonResponse(String requestType, Integer id);

    ProductDto getDtoFromProduct(Products products);

}
