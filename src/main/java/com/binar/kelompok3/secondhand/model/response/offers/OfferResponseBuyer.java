package com.binar.kelompok3.secondhand.model.response.offers;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import lombok.Data;

@Data
public class OfferResponseBuyer {

    private Integer id;
    private Integer status;
    private Double offerPrice;
    private String productId;

    public OfferResponseBuyer(Offers offers) {
        this.id = offers.getId();
        this.status = offers.getStatus();
        this.offerPrice = offers.getOfferPrice();
        this.productId = offers.getProductId().getId();

    }
}
