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

    @Query(value = "select * from products where publish=true", nativeQuery = true)
    Page<Products> getAllProductReadyPaginated(Pageable pageable);

    @Query(value = "select * from products where status=2 and user_id=?1", nativeQuery = true)
    Page<Products> getAllProductSoldPaginated(Integer userId, Pageable pageable);

    @Query(value = "select * from products where user_id=?1", nativeQuery = true)
    List<Products> getProductsByUserId(Integer userId);

    List<Products> findProductsByNameContainingIgnoreCase(String name, Pageable pageable); // ini

    List<Products> findProductsByCategoryContainingIgnoreCase(String category, Pageable pageable); // ini

    void deleteProductsById(String id);

    @Modifying
    @Query(value = "update products set name=?1, price=?2, status=?3, publish=?4, description=?5, category=?6 where id=?7", nativeQuery = true)
    Integer updateProducts(String name, Double price, Integer status, Integer publish,
                           String description, String category, String id);

    Products findProductsById(String id);

    @Query(value = "select DISTINCT ON (products.id) products.* from products inner join offers on" +
            " offers" +
            ".product_id \n" +
            "where products.user_id=?1 order by updated_on DESC",
            nativeQuery = true)
    Page<Products> getProductsDiminati(Integer id, Pageable pageable);

    Page<Products> findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndStatusAndPublish(String name, String category, Integer status, Integer publish, Pageable pageable);


}
