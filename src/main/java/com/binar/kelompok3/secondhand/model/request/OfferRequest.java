package com.binar.kelompok3.secondhand.model.request;

import lombok.*;


@Data
public class OfferRequest {

    private Integer status;
    private Double offerPrice;
    private String productId;

}
