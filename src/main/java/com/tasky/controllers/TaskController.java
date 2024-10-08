package com.tasky.controllers;

import com.tasky.models.Task;
import com.tasky.models.User;
import com.tasky.models.Category;
import com.tasky.services.TaskService;
import com.tasky.services.CategoryService;
import com.tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

/**
 * Controller for task management.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * Lists all tasks for the authenticated user.
     */
    @GetMapping
    public String listTasks(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Task> tasks = taskService.getTasksByUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    /**
     * Shows the form to create a new task.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Category> categories = categoryService.getCategoriesByUser(user);
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categories);
        return "create_task";
    }

    @PostMapping
    public String createTask(@ModelAttribute("task") Task task, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        task.setUser(user);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/complete")
    public String completeTask(@PathVariable Long id) {
        Task task = taskService.findTaskById(id);
        if (task != null) {
            task.setCompleted(true);
            taskService.saveTask(task);
        }
        return "redirect:/tasks";
    }


    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
