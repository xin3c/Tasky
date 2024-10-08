package com.tasky.services;

import com.tasky.models.User;
import com.tasky.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void testRegisterUser() {
        // Mock dependencies
        UserRepository userRepository = mock(UserRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserService userService = new UserService(userRepository, passwordEncoder);

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        // Call the method to test
        userService.registerUser(user);

        // Verify interactions
        verify(userRepository, times(1)).save(user);

        // Assert password is encrypted
        assertNotEquals("password", user.getPassword());
        assertTrue(passwordEncoder.matches("password", user.getPassword()));
    }
}
