package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OffersRepository extends JpaRepository<Offers, Integer> {

    @Query(value = "select * from offers", nativeQuery = true)
    List<Offers> getAllOffers();

    @Query(value = "select * from offers where user_id=?1 order by updated_on DESC", nativeQuery = true)
    List<Offers> findAllByUserId(Integer userId);

    @Query(value =
            "select offers.id, offers.created_on,offers.updated_on, offers.offer_price, offers.status, offers.product_id,offers.user_id\n" +
                    "from offers inner join(select id from products where products.user_id =?1)p on offers" +
                    ".product_id = p.id order by updated_on DESC; ", nativeQuery = true)
    List<Offers> getHistorySeller(Integer userId);

    Integer deleteOffersById(Integer id);

    @Modifying
    @Query(value = "update offers set status=?2 where id=?1", nativeQuery = true)
    Integer updateOffers(Integer id, Integer status);

    @Query(value ="select * from offers where id=?1" ,nativeQuery = true)
    Offers findOffersById(Integer id);

    @Modifying
    @Query(value ="update offers set status = 2 where product_id=?1 and id<>?2" ,nativeQuery = true)
    void setAllStatusToDeclined(String product_id,Integer id);

}
