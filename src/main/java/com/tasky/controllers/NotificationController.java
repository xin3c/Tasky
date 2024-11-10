package com.tasky.controllers;

import com.tasky.models.Notification;
import com.tasky.models.User;
import com.tasky.services.NotificationService;
import com.tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.tasky.services.NotificationService.loggerMan;


/**
 * The type Notification controller.
 */
@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notifService; //notificationService

    @Autowired
    private UserService userService;

    /**
     * Gets all notifications.
     *
     * @param model       the model
     * @param userDetails the user details
     * @return the all notifications
     */
    @GetMapping
    public String getAllNotifications(final Model model, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        final List<Notification> notifications = notifService.getAllNotifications(user);
        model.addAttribute("notifications", notifications);
        return "notifications";
    }

    /**
     * Show create form string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/new")
    public String showCreateForm(final Model model) {
        model.addAttribute("notification", new Notification());
        return "create_notification";
    }

    /**
     * Create notification string.
     *
     * @param notification the notification
     * @param userDetails  the user details
     * @return the string
     */
    @PostMapping
    public String createNotification(@ModelAttribute final Notification notification, @AuthenticationPrincipal final UserDetails userDetails) {
        loggerMan.info("CreateNotification with args: Notif: "+ notification + " UserDetails: " + userDetails);
        final User user = userService.findUserByUsername(userDetails.getUsername());
        notification.setUser(user);
        notification.setSendTime(LocalDateTime.now());
        notifService.saveNotification(notification);
        return "redirect:/notifications";
    }

    /**
     * Send notification string.
     *
     * @param id          the id
     * @param userDetails the user details
     * @return the string
     */
    @GetMapping("/send/{id}")
    public String sendNotification(@PathVariable final Long id, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        Notification notification = notifService.getAllNotifications(user)
                .stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (notification != null && !notification.isSent()) {
            notifService.sendNotification(notification);
        }
        return "redirect:/notifications";
    }
}
