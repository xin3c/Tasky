package com.tasky.controllers;

import com.tasky.models.Category;
import com.tasky.models.Task;
import com.tasky.models.User;
import com.tasky.services.CategoryService;
import com.tasky.services.TaskService;
import com.tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        Task task = taskService.findTaskById(id);
        LocalDate dueDate = task.getDueDate();
        if (!task.getUser().equals(user)) {
            return "redirect:/tasks";
        }
        List<Category> categories = categoryService.getCategoriesByUser(user);
        model.addAttribute("task", task);
        model.addAttribute("categories", categories);
        model.addAttribute("dueDate", dueDate);

        return "edit_task";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        task.setUser(user);
        if (task.getCategory() != null && task.getCategory().getId() != null) {
            Category category = categoryService.findCategoryById(task.getCategory().getId());
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        taskService.saveTask(task);
        return "redirect:/tasks";
    }
}
