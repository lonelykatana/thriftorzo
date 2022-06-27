package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductsRepository extends JpaRepository<Products, Integer> {

    @Query(value = "select * from products", nativeQuery = true)
    List<Products> getAllProducts();

    String deleteProductsById(Integer id);

    @Modifying
    @Query(value ="update products set name=?1, price=?2, status=?3, description=?4 where id=?5" ,nativeQuery = true)
    Integer updateProducts(String name, Double price, Integer status, String description, Integer id);

    Products findProductsById(Integer id);
}
