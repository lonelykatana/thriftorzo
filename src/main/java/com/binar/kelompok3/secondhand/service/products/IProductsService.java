package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.Products;

import java.util.List;

public interface IProductsService {

    String saveProducts(String name, Double price, Integer status, String description, Integer userId);

    String updateProducts(Integer id, String name, Double price, Integer status, String description, Integer userId);

    List<Products> getAllProducts();

    String deleteProductsById(Integer id);
}
