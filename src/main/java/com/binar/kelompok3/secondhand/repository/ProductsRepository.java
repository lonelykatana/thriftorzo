package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Products> findAllByOrderByCreatedOnDesc(Pageable pageable);

    @Query(value = "select * from products where publish=1", nativeQuery = true)
    Page<Products> getAllProductReadyPaginated(Pageable pageable);

    @Query(value = "select * from products where status=1 and user_id=?1", nativeQuery = true)
    Page<Products> getAllProductSoldPaginated(Integer userId, Pageable pageable);

    Page<Products> findProductsByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Products> findProductsByCategoryContainingIgnoreCase(String category, Pageable pageable);

    @Query(value = "select * from products where category=?1 and publish = 1", nativeQuery = true)
    Page<Products> getProductCategoryWhereReady(String category, Pageable pageable);

    String deleteProductsById(String id);

    @Modifying
    @Query(value = "update products set name=?1, price=?2, status=?3, publish=?4, description=?5, category=?6 where id=?7", nativeQuery = true)
    Integer updateProducts(String name, Double price, Integer status, Integer publish, String description, String category, String id);

    Products findProductsById(String id);


}
