package com.tasky.services;

import com.tasky.models.Category;
import com.tasky.models.Task;
import com.tasky.models.User;
import com.tasky.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for task-related operations.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Creates or updates a task.
     *
     * @param task the task to save
     * @return the saved task
     */
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Retrieves all tasks for a user.
     *
     * @param user the user
     * @return list of tasks
     */
    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    /**
     * Retrieves tasks by user and category.
     *
     * @param user     the user
     * @param category the category
     * @return list of tasks
     */
    public List<Task> getTasksByUserAndCategory(User user, Category category) {
        return taskRepository.findByUserAndCategory(user, category);
    }

    /**
     * Retrieves tasks by user and completion status.
     *
     * @param user      the user
     * @param completed the completion status
     * @return list of tasks
     */
    public List<Task> getTasksByUserAndStatus(User user, Boolean completed) {
        return taskRepository.findByUserAndCompleted(user, completed);
    }

    /**
     * Deletes a task.
     *
     * @param taskId the task ID
     */
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    /**
     * Finds a task by ID.
     *
     * @param taskId the task ID
     * @return the task
     */
    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }
}
