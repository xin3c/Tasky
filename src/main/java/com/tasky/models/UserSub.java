package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * The type User sub.
 */
@Data
@Entity
public class UserSub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String subscriptionJson;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Instantiates a new User sub.
     */
    public UserSub() {
    }

    /**
     * Instantiates a new User sub.
     *
     * @param subscriptionJson the subscription json
     * @param user             the user
     */
    public UserSub(final String subscriptionJson, final User user) {
        this.subscriptionJson = subscriptionJson;
        this.user = user;
    }

}
