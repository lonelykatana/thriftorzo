package com.binar.kelompok3.secondhand.service.offers;

import com.binar.kelompok3.secondhand.model.entity.Offers;

import java.util.List;

public interface IOffersService {

    List<Offers> getAllOffers();

    void saveOffers(Integer userId, String productId, Double offerPrice, Integer status);

    List<Offers> getAllByUserId(Integer userId);

    List<Offers> getHistorySeller(Integer userId);

    void updateOffers(Integer id, Integer status);

    void deleteOffersById(Integer id);

    Offers findOffersById(Integer id);

    Boolean getTransaction(Integer userId, String productId);

}
