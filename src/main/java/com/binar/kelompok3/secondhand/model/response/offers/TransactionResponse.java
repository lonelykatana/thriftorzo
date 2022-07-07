package com.binar.kelompok3.secondhand.model.response.offers;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.model.response.user.UserResponse;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionResponse {

    private Integer transactionId;
    private UserResponse buyerResponse;
    private ProductResponse productResponse;
    private Double offerPrice;
    private Date transactionDate;
    private Integer status;

    public TransactionResponse(Offers offers) {
        this.transactionId = offers.getId();
        this.buyerResponse = new UserResponse(offers.getUserId());
        this.productResponse = new ProductResponse(offers.getProductId(), offers.getProductId().getUserId());
        this.offerPrice = offers.getOfferPrice();
        this.transactionDate = offers.getCreatedOn();
        this.status = offers.getStatus();
    }
}
