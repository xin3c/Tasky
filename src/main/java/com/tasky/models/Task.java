package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;


    @Column(length = 1000)
    private String description;


    @Column(nullable = false)
    private Boolean completed = false;


    @Column(nullable = false)
    private String priority;


    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;


    @Column(nullable = false, updatable = false)
    private LocalDate dueDate;


    @Column(nullable = true)
    private LocalDateTime reminderTime;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<>();

    /**
     * Initializes creationDate before persisting.
     */
    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }
}
