package com.tasky.controllers;

import com.tasky.models.User;
import com.tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
     * Processes logout.
     *
     * @return lgout call
     */
    @GetMapping("/logout")
    public String logoutUser(Model model) {
        return "logout";
    }

    /**
     * Processes the registration form.
     *
     * @param user the user
     * @return redirect to login page
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (userService.usernameExists(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username already taken");
        }
        if (userService.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email already registered");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
}
