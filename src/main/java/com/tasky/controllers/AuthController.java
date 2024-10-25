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
 * The type Auth controller.
 */
@SuppressWarnings("ALL")
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Show login form string.
     *
     * @return the string
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    /**
     * Show registration form string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/register")
    public String showRegistrationForm(final Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    /**
     * Logout user string.
     *
     * @param ignoredModel the ignored model
     * @return the string
     */
    @GetMapping("/logout")
    public String logoutUser(Model ignoredModel) {
        return "logout";
    }


    /**
     * Register user string.
     *
     * @param user          the user
     * @param bindingResult the binding result
     * @return the string
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") final User user, final BindingResult bindingResult) {
        if (userService.usernameExists(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username already taken");
        }
        if (userService.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email already registered");
        }
        if (bindingResult.hasErrors()) { //NOPMD - suppressed OnlyOneReturn - TODO explain reason for suppression
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
}
