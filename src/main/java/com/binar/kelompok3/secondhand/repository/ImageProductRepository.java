package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct,String> {

    ImageProduct findImageProductByUrl(String url);
}
