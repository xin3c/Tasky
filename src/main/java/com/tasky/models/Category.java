package com.tasky.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Category.
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = false)
    private String name;

    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String icon_id;

    @OneToMany(mappedBy = "category")
    private Set<Task> tasks = new HashSet<>();
}
