package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    @Query(value = "select * from wishlist where user_id=?1 order by created_on desc ", nativeQuery = true)
    List<Wishlist> findAllByUserIdOrderByCreatedOnDesc(Integer userId);

    @Query(value = "select * from wishlist where product_id=?1 and user_id=?2", nativeQuery = true)
    List<Wishlist> getAWishlistByProductIdAndUserId(String productId, Integer userId);

    @Modifying
    @Query(value = "delete from wishlist where product_id=?1 and user_id=?2", nativeQuery = true)
    void deleteWishlistByProductIdAndUserId(String productId, Integer userId);

}
