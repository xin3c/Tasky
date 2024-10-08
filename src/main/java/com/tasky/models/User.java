package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity representing a registered user in the system.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    /**
     * Primary key for the User entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username for authentication.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Encrypted password for authentication.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Email address of the user.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Roles assigned to the user for authorization.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> roles = new HashSet<>();
}
