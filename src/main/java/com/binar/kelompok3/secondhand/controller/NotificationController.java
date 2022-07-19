package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Notification;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.request.notif.ReadNotificationRequest;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.notif.NotificationUnreadCountResponse;
import com.binar.kelompok3.secondhand.model.response.notif.NotificationPageResponse;
import com.binar.kelompok3.secondhand.model.response.notif.NotificationResponse;
import com.binar.kelompok3.secondhand.service.notification.INotificationService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/notification")
@Api(value = "/notification", tags = "Notification")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController {

    private INotificationService iNotificationService;
    private IUsersService usersService;

    @ApiOperation(value = "Read a notification.")
    @PutMapping("/read")
    public ResponseEntity<MessageResponse> readNotification(@RequestBody ReadNotificationRequest request) {
        iNotificationService.updateIsRead(request.getId());
        return ResponseEntity.ok(new MessageResponse("Notification Read."));
    }

    @ApiOperation(value = "Read all unread notifications for once.")
    @PutMapping("/all-read")
    public ResponseEntity<MessageResponse> markAllAsRead(Authentication authentication) {
        Users user = usersService.findByEmail(authentication.getName());
        iNotificationService.markAllAsRead(user.getId());
        return ResponseEntity.ok(new MessageResponse("All Notifications Read."));
    }

    @ApiOperation(value = "Counting unread notifications.")
    @GetMapping("/unread")
    public ResponseEntity<NotificationUnreadCountResponse> countNotifications(Authentication authentication) {
        Users user = usersService.findByEmail(authentication.getName());
        Integer unread = iNotificationService.unreadNotifications(user.getId());

        NotificationUnreadCountResponse response = new NotificationUnreadCountResponse(user, unread);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a notification.")
    @GetMapping("/get")
    public ResponseEntity<NotificationPageResponse> getNotificationAuth(Authentication authentication,
                                                                        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                        @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

        Users user = usersService.findByEmail(authentication.getName());
        List<Notification> notification = iNotificationService.getNotification(user.getId());
        List<NotificationResponse> notificationResponses =
                notification.stream()
                        .map(notification1 -> {
                            if (notification1.getOfferId() == null) {
                                return new NotificationResponse(notification1
                                        , notification1.getProductId());
                            } else return new NotificationResponse(notification1
                                    , notification1.getProductId(), notification1.getOfferId());
                        })
                        .collect(Collectors.toList());


        // buat bikin pageable
        int notifSize = notificationResponses.size();
        Pageable paging = PageRequest.of(page, size);
        int start = Math.min((int) paging.getOffset(), notifSize);
        int end = Math.min((start + paging.getPageSize()), notifSize);
        Page<NotificationResponse> paged = new PageImpl<>(notificationResponses.subList(start, end), paging, notifSize);
        // ribet emang,jangan diapapapin it worked > https://stackoverflow.com/questions/37749559/conversion-of-list-to-page-in-spring

        NotificationPageResponse pagedNotificationResponses = new NotificationPageResponse(paged, page);
        return new ResponseEntity<>(pagedNotificationResponses, HttpStatus.OK);
    }
}
