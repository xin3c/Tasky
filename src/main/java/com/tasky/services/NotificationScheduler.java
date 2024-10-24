package com.tasky.services;

import com.tasky.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedRate = 60000)
    public void scheduleNotification() {
        List<Notification> pendingNotifications = notificationService.getPendingNotifications();
        for (Notification notification : pendingNotifications) {
            notificationService.sendNotification(notification);
        }
    }
}
