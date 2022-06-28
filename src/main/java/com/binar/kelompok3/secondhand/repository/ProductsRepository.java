package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.dto.IImageAndProductDto;
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

    @Query(value = "select * from products", nativeQuery = true)
    Page<Products> getAllProductsPaginated(Pageable pageable);

    Page<Products> findProductsByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Products> findProductsByCategoryContainingIgnoreCase(String category, Pageable pageable);

    String deleteProductsById(Integer id);

    @Modifying
    @Query(value = "update products set name=?1, price=?2, status=?3, description=?4 where id=?5", nativeQuery = true)
    Integer updateProducts(String name, Double price, Integer status, String description, Integer id);

    Products findProductsById(Integer id);

    @Modifying
    @Query(value =
            "select p.id as id, p.name as name, p.price as price, p.status as status, p.description as description, p.category as category\n" +
                    ",p.user_id as user_id, p.created_on as created_on, p.updated_on as updated_on, ip.url  as url \n" +
                    "from products p join image_product ip on p.id = ip.product_id where p.id =?1",
            nativeQuery = true)
    List<IImageAndProductDto> getProductsAndImage(Integer id);


}
