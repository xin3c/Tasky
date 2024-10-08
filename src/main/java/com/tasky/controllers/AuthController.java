package com.tasky.controllers;

import com.tasky.models.User;
import com.tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Controller for authentication and registration.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Shows the login page.
     *
     * @return login view
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Shows the registration page.
     *
     * @return registration view
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Processes the registration form.
     *
     * @param user the user
     * @return redirect to login page
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Username already taken");
            return "register";
        }
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
}
