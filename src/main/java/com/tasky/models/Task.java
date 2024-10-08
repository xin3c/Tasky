package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Task entity representing a user's task.
 */
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {

    /**
     * Primary key for the Task entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the task.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Detailed description of the task.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Status of the task (Completed / Not Completed).
     */
    @Column(nullable = false)
    private Boolean completed = false;

    /**
     * Date and time when the task was created.
     */
    @Column(nullable = false)
    private LocalDateTime creationDate;

    /**
     * Due date and time for the task.
     */
    private LocalDateTime dueDate;

    /**
     * User who owns the task.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Category to which the task belongs.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Initializes creationDate before persisting.
     */
    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }
}
