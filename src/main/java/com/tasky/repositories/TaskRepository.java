package com.tasky.repositories;

import com.tasky.models.Category;
import com.tasky.models.Task;
import com.tasky.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The interface Task repository.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {


    /**
     * Find by user list.
     *
     * @param user the user
     * @return the list
     */
    List<Task> findByUser(User user);


    /**
     * Find by user and category list.
     *
     * @param user     the user
     * @param category the category
     * @return the list
     */
    List<Task> findByUserAndCategory(User user, Category category);


}
