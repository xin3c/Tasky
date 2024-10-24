package com.tasky.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasky.models.Notification;
import com.tasky.models.UserSubscription;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for handling notifications.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserSubscriptionService userSubscriptionService;

    private PushService pushService;

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

    @Value("${vapid.public.key}")
    private String publicKeyStr;

    @Value("${vapid.private.key}")
    private String privateKeyStr;

    @Value("${vapid.subject}")
    private String subject;


    @PostConstruct
    public void init() throws GeneralSecurityException, IOException {
        Security.addProvider(new BouncyCastleProvider());

        ECPrivateKey privateKey = (ECPrivateKey) Utils.loadPrivateKey(privateKeyStr);
        ECPublicKey publicKey = (ECPublicKey) Utils.loadPublicKey(publicKeyStr);

        pushService = new PushService()
                .setPrivateKey(privateKey)
                .setPublicKey(publicKey)
                .setSubject(subject);
    }

    public void sendNotification(Notification notification) {
        User user = notification.getUser();
        UserSubscription subscriptionEntity = userSubscriptionService.getSubscriptionByUser(user);

        if (subscriptionEntity != null) {
            try {
                nl.martijndwars.webpush.Subscription webPushSubscription =
                        objectMapper.readValue(subscriptionEntity.getSubscriptionJson(), nl.martijndwars.webpush.Subscription.class);

                String payload = "{\"title\":\"Напоминание\",\"body\":\"" + notification.getMessage() + "\",\"url\":\"/tasks\"}";

                nl.martijndwars.webpush.Notification webPushNotification = new nl.martijndwars.webpush.Notification(
                        webPushSubscription,
                        payload
                );

                HttpResponse response = pushService.send(webPushNotification);
                if (response.getStatusLine().getStatusCode() == 201) {
                    notification.setSent(true);
                    notificationRepository.save(notification);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public List<Notification> getPendingNotifications() {
        return notificationRepository.findByIsSentFalseAndSendTimeBefore(LocalDateTime.now());
    }
    public List<Notification> getAllNotifications(User user) {
        return notificationRepository.findByUser(user);
    }

    public void saveNotification(Notification notification) { notificationRepository.save(notification); }

    public Notification findByTask(Task task) { return notificationRepository.findByTask(task); }
    public void deleteNotification(Notification notification) {
        notificationRepository.delete(notification);
    }
}
