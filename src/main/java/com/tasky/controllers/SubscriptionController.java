package com.tasky.controllers;

import com.tasky.models.User;
import com.tasky.services.UserService;
import com.tasky.services.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * The type Subscription controller.
 */
@RestController
public class SubscriptionController {

    @Autowired
    private UserSubscriptionService subService;

    @Autowired
    private UserService userService;

    /**
     * Subscribe.
     *
     * @param subscriptionJson the subscription json
     * @param userDetails      the user details
     */
    @PostMapping("/subscription")
    public void subscribe(@RequestBody final String subscriptionJson, @AuthenticationPrincipal final UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        subService.saveSubscription(subscriptionJson, user);
    }
}
