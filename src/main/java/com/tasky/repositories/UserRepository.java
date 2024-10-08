package com.tasky.repositories;

import com.tasky.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username.
     */
    User findByUsername(String username);

    /**
     * Find user by email.
     */
    User findByEmail(String email);
}
