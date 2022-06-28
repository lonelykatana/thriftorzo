package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductsService {

    void saveProducts(String name, Double price, Integer status, String description, Integer userId);

    void updateProducts(String name, Double price, Integer status, String description, Integer id);

    List<Products> getAllProducts();

    Page<Products> getAllProductsPaginated(Pageable pageable);

    void deleteProductsById(Integer id);

    Products findProductsById(Integer id);
}
