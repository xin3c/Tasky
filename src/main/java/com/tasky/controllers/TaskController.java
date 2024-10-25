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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.tasky.services.TaskService.VALID_STATUSES;


/**
 * The type Task controller.
 */
@SuppressWarnings("SameReturnValue")
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
    private NotificationService notifService;


    /**
     * List tasks string.
     *
     * @param model       the model
     * @param userDetails the user details
     * @return the string
     */
    @GetMapping
    public String listTasks(final Model model, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());

        final List<Task> tasks = taskService.getTasksByUser(user);
        final LocalDate today = LocalDate.now();
        final LocalDate tomorrow = today.plusDays(1);
        final LocalDate weekEnd = today.plusDays(7);

        final List<Task> overdueTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(today))
                .collect(Collectors.toList());

        final List<Task> todayTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isEqual(today))
                .collect(Collectors.toList());

        final List<Task> tomorrowTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isEqual(tomorrow))
                .collect(Collectors.toList());

        final List<Task> thisWeekTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isAfter(tomorrow) && task.getDueDate().isBefore(weekEnd))
                .collect(Collectors.toList());

        final List<Task> laterTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isAfter(weekEnd))
                .collect(Collectors.toList());

        List<Map<String, Object>> taskGroups = new ArrayList<>();
        taskGroups.add(Map.of("title", "Overdue", "tasks", overdueTasks));
        taskGroups.add(Map.of("title", "Today", "tasks", todayTasks));
        taskGroups.add(Map.of("title", "Tomorrow", "tasks", tomorrowTasks));
        taskGroups.add(Map.of("title", "This Week", "tasks", thisWeekTasks));
        taskGroups.add(Map.of("title", "Later", "tasks", laterTasks));

        model.addAttribute("taskGroups", taskGroups);

        return "tasks";
    }


    /**
     * Show create form string.
     *
     * @param model       the model
     * @param userDetails the user details
     * @return the string
     */
    @GetMapping("/new")
    public String showCreateForm(final Model model, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        final List<Category> categories = categoryService.getCategoriesByUser(user);
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categories);
        model.addAttribute("priorities", new String[]{"Low", "Medium", "High"});
        model.addAttribute("statuses", VALID_STATUSES);

        return "create_task";
    }

    /**
     * Create task string.
     *
     * @param task        the task
     * @param userDetails the user details
     * @return the string
     */
    @PostMapping
    public String createTask(@ModelAttribute("task") final Task task, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        task.setUser(user);
        taskService.createOrUpdateTask(task);
        if (task.getReminderTime() != null) {
            final Notification notification = new Notification();
            notification.setMessage("Task remind: " + task.getTitle());
            notification.setSendTime(task.getReminderTime());
            notification.setUser(user);
            notification.setTask(task);
            notifService.saveNotification(notification);
        }
        return "redirect:/tasks";


    }


    /**
     * Delete task string.
     *
     * @param id the id
     * @return the string
     */
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable final Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    /**
     * Show edit form string.
     *
     * @param id          the id
     * @param model       the model
     * @param userDetails the user details
     * @return the string
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable final Long id, final Model model, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        final Task task = taskService.findTaskById(id);
        final LocalDate dueDate = task.getDueDate();
        if (!task.getUser().equals(user)) { //NOPMD - suppressed OnlyOneReturn - TODO explain reason for suppression
            return "redirect:/tasks";
        }
        final List<Category> categories = categoryService.getCategoriesByUser(user);
        model.addAttribute("task", task);
        model.addAttribute("categories", categories);
        model.addAttribute("dueDate", dueDate);
        model.addAttribute("priorities", new String[]{"Low", "Medium", "High"});
        model.addAttribute("statuses", VALID_STATUSES);
        return "edit_task";
    }

    /**
     * Update task string.
     *
     * @param task        the task
     * @param userDetails the user details
     * @return the string
     */
    @PostMapping("/update")
    public String updateTask(@ModelAttribute final Task task, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        task.setUser(user);
        if (task.getCategory() != null && task.getCategory().getId() != null) {
            final Category category = categoryService.findCategoryById(task.getCategory().getId());
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        taskService.createOrUpdateTask(task);
        if (task.getReminderTime() != null) {
            Notification notification = notifService.findByTask(task);
            if (notification == null) {
                notification = new Notification();
            }
            notification.setMessage("Reminder for " + task.getTitle());
            notification.setSendTime(task.getReminderTime());
            notification.setUser(user);
            notification.setTask(task);
            notifService.saveNotification(notification);
        }

        return "redirect:/tasks";
    }


    @PostMapping("/update-status/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateTaskStatus(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        Optional<Task> taskOptional = Optional.ofNullable(taskService.findTaskById(id));

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            String newStatus = requestBody.get("status");

            if (VALID_STATUSES.contains(newStatus)) {
                task.setStatus(newStatus);
                taskService.saveTask(task);

                Map<String, Object> response = new HashMap<>();
                response.put("status", newStatus);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Invalid status"));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
