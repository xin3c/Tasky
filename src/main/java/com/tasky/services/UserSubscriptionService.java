package com.tasky.services;

import com.tasky.models.User;
import com.tasky.models.UserSubscription;
import com.tasky.repositories.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSubscriptionService {

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    public void saveSubscription(String subscriptionJson, User user) {
        UserSubscription existingSubscription = getSubscriptionByUser(user);
        if (existingSubscription != null) {
            existingSubscription.setSubscriptionJson(subscriptionJson);
            userSubscriptionRepository.save(existingSubscription);
        } else {
            UserSubscription subscription = new UserSubscription(subscriptionJson, user);
            userSubscriptionRepository.save(subscription);
        }
    }

    public UserSubscription getSubscriptionByUser(User user) {
        return userSubscriptionRepository.findByUser(user).stream().findFirst().orElse(null);
    }
}
