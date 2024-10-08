package com.tasky.services;

import com.tasky.models.User;
import com.tasky.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor injection of UserRepository and BCryptPasswordEncoder.
     *
     * @param userRepository the user repository
     * @param passwordEncoder the password encoder
     */
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user with encrypted password.
     *
     * @param user the user to register
     * @return the saved user
     */
    public User registerUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Assign default role
        user.getRoles().add("USER");
        return userRepository.save(user);
    }

    /**
     * Finds a user by username.
     *
     * @param username the username
     * @return the user if found, else null
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Checks if a username already exists.
     *
     * @param username the username
     * @return true if username exists, else false
     */
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    /**
     * Checks if an email already exists.
     *
     * @param email the email
     * @return true if email exists, else false
     */
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
