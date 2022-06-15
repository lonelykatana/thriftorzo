package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
