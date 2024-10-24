package com.tasky.controllers;

import com.tasky.models.Category;
import com.tasky.models.Notification;
import com.tasky.models.Task;
import com.tasky.models.User;
import com.tasky.services.CategoryService;
import com.tasky.services.NotificationService;
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
import java.util.stream.Collectors;

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

    @Autowired
    private NotificationService notificationService;


    @GetMapping
    public String listTasks(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());

        List<Task> tasks = taskService.getTasksByUser(user);
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate weekEnd = today.plusDays(7);


        List<Task> todayTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isEqual(today))
                .collect(Collectors.toList());

        List<Task> tomorrowTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isEqual(tomorrow))
                .collect(Collectors.toList());

        List<Task> thisWeekTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isAfter(tomorrow) && task.getDueDate().isBefore(weekEnd))
                .collect(Collectors.toList());

        List<Task> laterTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isAfter(weekEnd))
                .collect(Collectors.toList());

        model.addAttribute("todayTasks", todayTasks);
        model.addAttribute("tomorrowTasks", tomorrowTasks);
        model.addAttribute("thisWeekTasks", thisWeekTasks);
        model.addAttribute("laterTasks", laterTasks);

        // Возвращаем шаблон tasks.html
        return "tasks";
    }



    @GetMapping("/new")
    public String showCreateForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Category> categories = categoryService.getCategoriesByUser(user);
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categories);
        model.addAttribute("priorities", new String[]{"Low", "Medium", "High"});

        return "create_task";
    }

    @PostMapping
    public String createTask(@ModelAttribute("task") Task task, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        task.setUser(user);
        taskService.saveTask(task);
        if (task.getReminderTime() != null) {
            Notification notification = new Notification();
            notification.setMessage("Task remind: " + task.getTitle());
            notification.setSendTime(task.getReminderTime());
            notification.setUser(user);
            notification.setTask(task);
            notificationService.saveNotification(notification);
        }
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


    @GetMapping("/delete/{id}")
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
        model.addAttribute("priorities", new String[]{"Low", "Medium", "High"});
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

        // Обновляем или создаем уведомление при обновлении задачи
        if (task.getReminderTime() != null) {
            Notification notification = notificationService.findByTask(task);
            if (notification == null) {
                notification = new Notification();
            }
            notification.setMessage("Напоминание о задаче: " + task.getTitle());
            notification.setSendTime(task.getReminderTime());
            notification.setUser(user);
            notification.setTask(task);
            notificationService.saveNotification(notification);
        }

        return "redirect:/tasks";
    }

}
