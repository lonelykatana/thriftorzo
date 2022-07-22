package com.binar.kelompok3.secondhand.service.notification;

import com.binar.kelompok3.secondhand.model.entity.Notification;
import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Products;

import java.util.List;

public interface INotificationService {
    void saveNotification(String title, String info, Integer roles, Offers offers, Products productId, Integer userId);

    void saveNotification(String title, String info, Products products, Integer roles, Integer userId);

    void updateIsRead(Integer id);

    void markAllAsRead(Integer userId);

    List<Notification> getNotification(Integer userId);

    Integer unreadNotifications(Integer userId);

    void saveNotification(String title, String info, Products products, Integer roles);
}
