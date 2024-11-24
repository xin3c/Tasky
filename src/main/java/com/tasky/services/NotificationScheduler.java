package com.tasky.services;

import com.tasky.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Notification scheduler.
 */
@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    /**
     * Schedule notification.
     */
    @Scheduled(fixedRate = 10_000)
    public void scheduleNotification() {
        List<Notification> pendingNotifications = notificationService.getPendingNotifications();
        for (Notification notification : pendingNotifications) {
            notificationService.sendNotification(notification);
        }
    }
}
