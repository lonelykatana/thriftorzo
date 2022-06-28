package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct,String> {

    ImageProduct findImageProductByUrl(String url);

    ImageProduct findImageProductByCreatedOn(Date createdOn);

    List<ImageProduct> findImageProductByProducts(Integer Products);

}
