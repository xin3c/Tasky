package com.tasky.services;

import com.tasky.models.User;
import com.tasky.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * The type User service.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * Instantiates a new User service.
     *
     * @param userRepository  the user repository
     * @param passwordEncoder the password encoder
     */
    public UserService(final UserRepository userRepository, final BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Register user.
     *
     * @param user the user
     */
    public void registerUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add("USER");
        userRepository.save(user);
    }


    /**
     * Find user by username user.
     *
     * @param username the username
     * @return the user
     */
    public User findUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }


    /**
     * Username exists boolean.
     *
     * @param username the username
     * @return the boolean
     */
    public boolean usernameExists(final String username) {
        return userRepository.findByUsername(username) != null;
    }


    /**
     * Email exists boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
}
