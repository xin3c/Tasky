package com.tasky.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasky.models.Notification;
import com.tasky.models.UserSub;
import com.tasky.models.Task;
import com.tasky.models.User;
import com.tasky.repositories.NotificationRepository;
import jakarta.annotation.PostConstruct;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import nl.martijndwars.webpush.Utils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The type Notification service.
 */
@Service
public class NotificationService {
    public static final Logger loggerMan = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notifRepo;

    @Autowired
    private UserSubscriptionService userSubService;

    private PushService pushService;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${vapid.public.key}")
    private String publicKeyStr;

    @Value("${vapid.private.key}")
    private String privateKeyStr;

    @Value("${vapid.subject}")
    private String subject;


    /**
     * Init.
     *
     * @throws GeneralSecurityException the general security exception
     */
    @PostConstruct
    public void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());

        final ECPrivateKey privateKey = (ECPrivateKey) Utils.loadPrivateKey(privateKeyStr);
        final ECPublicKey publicKey = (ECPublicKey) Utils.loadPublicKey(publicKeyStr);

        pushService = new PushService()
                .setPrivateKey(privateKey)
                .setPublicKey(publicKey)
                .setSubject(subject);
    }

    /**
     * Send notification.
     *
     * @param notification the notification
     */
    public void sendNotification(final Notification notification) {
        // loggerMan.info("Entering NotificationService.void sendNotification with args: final Notification notification");
        final User user = notification.getUser();
        final UserSub subEntity = userSubService.getSubscriptionByUser(user);
        if (subEntity == null){
            // loggerMan.info("SubEntity = null. {}, {}, {}", user.getUsername(), notification.isSent(), notification.getMessage());
        }
        else {
            try {
                loggerMan.info("Subscription JSON: {}", subEntity.getSubscriptionJson());

                final nl.martijndwars.webpush.Subscription webPushSubscription =
                        objectMapper.readValue(subEntity.getSubscriptionJson(), nl.martijndwars.webpush.Subscription.class);

                final String payload = "{\"title\":\"Reminder: \",\"body\":\"" + notification.getMessage() + "\",\"url\":\"/tasks\"}";

                final nl.martijndwars.webpush.Notification webPushNotification = new nl.martijndwars.webpush.Notification(
                        webPushSubscription,
                        payload
                );

                final HttpResponse response = pushService.send(webPushNotification);
                if (response.getStatusLine().getStatusCode() == 201) {
                    notification.setSent(true);
                    loggerMan.info("Notification sent successfully to user: {}", user.getUsername());

                    notifRepo.save(notification);
                }
                loggerMan.info("HTTP Response Status: {}", response.getStatusLine().getStatusCode());

            } catch (Exception e) {
                if (loggerMan.isErrorEnabled()) {
                    loggerMan.error("Error sending notification to user: {} - {}", user.getUsername(), e.getMessage(), e);
                }

            }
        }
    }


    /**
     * Gets pending notifications.
     *
     * @return the pending notifications
     */
    final public List<Notification> getPendingNotifications() {
        return notifRepo.findByIsSentFalseAndSendTimeBefore(LocalDateTime.now());
    }

    /**
     * Gets all notifications.
     *
     * @param user the user
     * @return the all notifications
     */
    final public List<Notification> getAllNotifications(final User user) {
        return notifRepo.findByUser(user);
    }

    /**
     * Save notification.
     *
     * @param notification the notification
     */
    public void saveNotification(final Notification notification) {
        loggerMan.info("SaveNotification. User:  {}, Msg: {}", notification.getUser(), notification.getMessage());
        notifRepo.save(notification);
    }

    /**
     * Find by task notification.
     *
     * @param task the task
     * @return the notification
     */
    public Notification findByTask(final Task task) {
        // loggerMan.info("Entering NotificationService.Notification findByTask with args: final Task task");
        return notifRepo.findByTask(task);
    }

    /**
     * Delete notification.
     *
     * @param notification the notification
     */
    public void deleteNotification(final Notification notification) {
        // loggerMan.info("Entering NotificationService.void deleteNotification with args: final Notification notification");
        notifRepo.delete(notification);
    }
}
