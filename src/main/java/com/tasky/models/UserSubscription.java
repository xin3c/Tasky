package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String subscriptionJson;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserSubscription() {
    }

    public UserSubscription(String subscriptionJson, User user) {
        this.subscriptionJson = subscriptionJson;
        this.user = user;
    }

}
