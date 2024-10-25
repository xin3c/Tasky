package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type Notification.
 */
@Data
@NoArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime sendTime;

    private boolean isSent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;


    /**
     * Instantiates a new Notification.
     *
     * @param message  the message
     * @param sendTime the send time
     * @param user     the user
     * @param task     the task
     */
    public Notification(final String message, final LocalDateTime sendTime, final User user, final Task task) {
        this.message = message;
        this.sendTime = sendTime;
        this.user = user;
        this.task = task;
        this.isSent = false;
    }


}
