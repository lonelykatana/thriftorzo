package com.binar.kelompok3.secondhand.service.notification;

import com.binar.kelompok3.secondhand.model.entity.Notification;
import com.binar.kelompok3.secondhand.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private INotificationService service = new NotificationServiceImpl();

    @Test
    void getNotification() {
        Integer userId = 1;

        List<Notification> notifications = new ArrayList<>();

        when(repository.findNotifications(userId)).thenReturn(notifications);

        assertSame(notifications, service.getNotification(userId));
    }
}