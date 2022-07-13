package com.binar.kelompok3.secondhand.model.response.notif;

import com.binar.kelompok3.secondhand.model.entity.Notification;
import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class NotificationResponse {

    private Integer id;
    private Integer transactionId;
    private String title;
    private Double offerPrice;
    private String info;
    private Boolean isRead;
    private Date lastUpdated;
    private ProductResponse productResponse;
    private Integer userId;

    public NotificationResponse(Notification notification, Products products, Offers offers) {
        this.id = notification.getId();
        this.transactionId = offers.getId();
        this.title = notification.getTitle();
        this.offerPrice = offers.getOfferPrice();
        this.info = notification.getInfo();
        this.isRead = notification.getIsRead();
        this.lastUpdated = notification.getUpdatedOn();
        this.productResponse = new ProductResponse(products, products.getUserId());
        this.userId = notification.getUserId().getId();
    }

    public NotificationResponse(Notification notification, Products products) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.info = notification.getInfo();
        this.isRead = notification.getIsRead();
        this.lastUpdated = notification.getUpdatedOn();
        this.productResponse = new ProductResponse(products, products.getUserId());
        this.userId = notification.getUserId().getId();
    }
}
