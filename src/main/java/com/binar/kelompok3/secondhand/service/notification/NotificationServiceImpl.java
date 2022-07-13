package com.binar.kelompok3.secondhand.service.notification;

import com.binar.kelompok3.secondhand.model.entity.Notification;
import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.NotificationRepository;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private NotificationRepository notificationRepository;
    private IUsersService iUsersService;

    @Override
    public void saveNotification(String title, String info, Offers offers,
                                 Products productId, Integer userId) {
        Notification notification = new Notification();
        Users users = iUsersService.findUsersById(userId);
        notification.setTitle(title);
        notification.setInfo(info);
        notification.setProductId(productId);
        notification.setUserId(users);
        notification.setOfferId(offers);
        notificationRepository.save(notification);
    }

    @Override
    public void saveNotification(String title, Products products, Integer userId) {
        Notification notification = new Notification();
        Users users = iUsersService.findUsersById(userId);
        notification.setTitle(title);
        notification.setProductId(products);
        notification.setUserId(users);
        notificationRepository.save(notification);

    }

    @Override
    public void updateIsRead(Integer id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        notification.ifPresent(notification1 -> {
            notification1.setIsRead(true);
            notificationRepository.save(notification1);
        });
    }

    @Override
    public List<Notification> getNotification(Integer userId) {
        return notificationRepository.findNotifications(userId);
    }

    @Override
    public Integer unreadNotifications(Integer userId) {
        return notificationRepository.unreadNotifications(userId);
    }
}
