package com.binar.kelompok3.secondhand.model.response;

import com.binar.kelompok3.secondhand.model.entity.Notification;
import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Products;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationResponse {

    private Integer id;
    private String title;
    private Double offerPrice;
    private String info;
    private Boolean isRead;
    private String productName;
    private Double productPrice;
    private String productUrl;
    private Integer userId;

    public NotificationResponse(Notification notification, Products products, Offers offers) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.offerPrice = offers.getOfferPrice();
        this.info = notification.getInfo();
        this.isRead = notification.getIsRead();
        this.productName = products.getName();
        this.productPrice = products.getPrice();
        this.productUrl = products.getImageProducts().get(0).getUrl();
        this.userId = notification.getUserId().getId();
    }

    public NotificationResponse(Notification notification, Products products) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.info = notification.getInfo();
        this.isRead = notification.getIsRead();
        this.productName = products.getName();
        this.productPrice = products.getPrice();
        this.productUrl = products.getImageProducts().get(0).getUrl();
        this.userId = notification.getUserId().getId();
    }
}
