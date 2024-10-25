package com.tasky.services;

import com.tasky.models.Category;
import com.tasky.models.Task;
import com.tasky.models.User;
import com.tasky.repositories.TaskRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * The type Task service.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public static final List<String> VALID_STATUSES = Arrays.asList("Not started", "In progress", "Completed");


    /**
     * Save task.
     *
     * @param task the task
     */
    public void saveTask(final Task task) {
        taskRepository.save(task);
    }


    /**
     * Gets tasks by user.
     *
     * @param user the user
     * @return the tasks by user
     */
    public List<Task> getTasksByUser(final User user) {
        return taskRepository.findByUser(user);
    }


    /**
     * Gets tasks by user and category.
     *
     * @param user     the user
     * @param category the category
     * @return the tasks by user and category
     */
    public List<Task> getTasksByUserAndCategory(final User user, final Category category) {
        return taskRepository.findByUserAndCategory(user, category);
    }





    /**
     * Delete task.
     *
     * @param taskId the task id
     */
    @Transactional
    public void deleteTask(final Long taskId) {
        taskRepository.deleteById(taskId);
    }


    /**
     * Find task by id task.
     *
     * @param taskId the task id
     * @return the task
     */
    public Task findTaskById(final Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    /**
     * Validates the status of the task.
     *
     * @param status the status to validate
     */
    private void validateTaskStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid task status: " + status);
        }
    }
    /**
     * Validates and saves a task.
     *
     * @param task the task to save
     * @return the saved task
     */
    public Task createOrUpdateTask(Task task) {
        validateTaskStatus(task.getStatus());
        return taskRepository.save(task);
    }
}
