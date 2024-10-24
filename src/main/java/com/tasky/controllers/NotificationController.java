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

/**
 * Controller for notification management.
 */
@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllNotifications(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Notification> notifications = notificationService.getAllNotifications(user);
        model.addAttribute("notifications", notifications);
        return "notifications";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("notification", new Notification());
        return "create_notification";
    }

    @PostMapping
    public String createNotification(@ModelAttribute Notification notification, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        notification.setUser(user);
        notification.setSendTime(LocalDateTime.now()); // Или установить время отправки по желанию пользователя
        notificationService.saveNotification(notification);
        return "redirect:/notifications";
    }

    @GetMapping("/send/{id}")
    public String sendNotification(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        Notification notification = notificationService.getAllNotifications(user)
                .stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (notification != null && !notification.isSent()) {
            notificationService.sendNotification(notification);
        }
        return "redirect:/notifications";
    }
}
