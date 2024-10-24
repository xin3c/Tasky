package com.tasky.controllers;

import com.tasky.models.User;
import com.tasky.services.UserService;
import com.tasky.services.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {

    @Autowired
    private UserSubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @PostMapping("/subscription")
    public void subscribe(@RequestBody String subscriptionJson, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        subscriptionService.saveSubscription(subscriptionJson, user);
    }
}
