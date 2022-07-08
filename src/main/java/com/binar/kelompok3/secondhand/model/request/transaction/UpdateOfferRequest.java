package com.binar.kelompok3.secondhand.model.request.transaction;

import lombok.Data;

@Data
public class UpdateOfferRequest {
    private Integer offerId;
    private Integer status;
}
