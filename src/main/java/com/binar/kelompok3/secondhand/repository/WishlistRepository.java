package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findAllByUserIdOrderByCreatedOnDesc(Integer userId);

    void deleteById(Integer id);

}
