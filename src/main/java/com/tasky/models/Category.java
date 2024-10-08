package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Category entity representing a grouping of tasks.
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {

    /**
     * Primary key for the Category entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the category.
     */
    @Column(nullable = false)
    private String name;

    /**
     * User who owns the category.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Set of tasks associated with this category.
     */
    @OneToMany(mappedBy = "category")
    private Set<Task> tasks = new HashSet<>();
}
