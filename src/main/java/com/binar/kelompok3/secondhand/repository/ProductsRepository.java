package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
