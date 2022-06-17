package com.binar.kelompok3.secondhand.service.offers;

import com.binar.kelompok3.secondhand.model.Offers;

import java.util.List;

public interface IOffersService {

    List<Offers> getAllOffers();

    String saveOffers(Integer userId, Integer productId, Double offerPrice, Integer status);

    List<Offers> getAllByUserId(Integer userId);

    List<Offers> getAllByProductId(Integer productId);

    Integer updateOffers(Integer id, Integer status);

    Integer deleteOffersById(Integer id);



}
