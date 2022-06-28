package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.dto.IImageAndProductDto;
import com.binar.kelompok3.secondhand.dto.ProductDto;
import com.binar.kelompok3.secondhand.model.entity.Products;

import java.util.LinkedHashMap;
import java.util.List;

public interface IProductsService {

    void saveProducts(String name, Double price, Integer status, String description, String Category,
                      Integer userId);

    void updateProducts(String name, Double price, Integer status, String description, Integer id);

    List<Products> getAllProducts();

    void deleteProductsById(Integer id);

    Products findProductsById(Integer id);

    List<IImageAndProductDto> getProductsAndImage(Integer id);

    LinkedHashMap<String, Object> modifyJsonResponse(String requestType, Integer id);

    public ProductDto getDtoFromProduct(Products products);

}
