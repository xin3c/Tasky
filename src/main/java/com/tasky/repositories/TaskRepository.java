package com.tasky.repositories;

import com.tasky.models.Category;
import com.tasky.models.Task;
import com.tasky.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Task entity.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Finds tasks by user.
     *
     * @param user the user
     * @return list of tasks
     */
    List<Task> findByUser(User user);

    /**
     * Finds tasks by user and category.
     *
     * @param user     the user
     * @param category the category
     * @return list of tasks
     */
    List<Task> findByUserAndCategory(User user, Category category);

    /**
     * Finds tasks by user and completion status.
     *
     * @param user      the user
     * @param completed the completion status
     * @return list of tasks
     */
    List<Task> findByUserAndCompleted(User user, Boolean completed);
}
