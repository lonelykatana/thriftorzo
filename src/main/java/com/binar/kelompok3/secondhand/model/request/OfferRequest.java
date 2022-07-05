package com.binar.kelompok3.secondhand.model.request;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.service.offers.IOffersService;
import lombok.*;


@Data
public class OfferRequest {


    private Integer status;
    private Double offerPrice;
    private Integer userId;
    private String productId;


}
