package com.binar.kelompok3.secondhand.model.response.offers;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import lombok.Data;

@Data
public class OfferResponseSeller {

    private Integer id;
    private Integer status;
    private Double offerPrice;
    private Integer userId;
    private String productId;

    public OfferResponseSeller(Offers offers) {
        this.id = offers.getId();
        this.status = offers.getStatus();
        this.offerPrice = offers.getOfferPrice();
        this.userId = offers.getUserId().getId();
        this.productId = offers.getProductId().getId();

    }
}
