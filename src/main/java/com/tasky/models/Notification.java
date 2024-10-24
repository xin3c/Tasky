package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    // Связь с задачей
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;


    public Notification(String message, LocalDateTime sendTime, User user, Task task) {
        this.message = message;
        this.sendTime = sendTime;
        this.user = user;
        this.task = task;
        this.isSent = false;
    }


}
