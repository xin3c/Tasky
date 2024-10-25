package com.tasky.services;

import com.tasky.models.User;
import com.tasky.models.UserSub;
import com.tasky.repositories.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type User subscription service.
 */
@Service
public class UserSubscriptionService {

    @Autowired
    private UserSubscriptionRepository usrSubRepo;

    /**
     * Save subscription.
     *
     * @param subscriptionJson the subscription json
     * @param user             the user
     */
    public void saveSubscription(final String subscriptionJson, final User user) {
        final UserSub existingSubscription = getSubscriptionByUser(user);
        if (existingSubscription != null) {
            existingSubscription.setSubscriptionJson(subscriptionJson);
            usrSubRepo.save(existingSubscription);
        } else {
            final UserSub subscription = new UserSub(subscriptionJson, user);
            usrSubRepo.save(subscription);
        }
    }

    /**
     * Gets subscription by user.
     *
     * @param user the user
     * @return the subscription by user
     */
    public final UserSub getSubscriptionByUser(final User user) {
        return usrSubRepo.findByUser(user).stream().findFirst().orElse(null);
    }
}
