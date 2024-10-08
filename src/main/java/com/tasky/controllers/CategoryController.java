package com.tasky.controllers;

import com.tasky.models.Category;
import com.tasky.models.User;
import com.tasky.services.CategoryService;
import com.tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Controller for category management.
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * Lists all categories for the authenticated user.
     */
    @GetMapping
    public String listCategories(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("categories", categoryService.getCategoriesByUser(user));
        return "categories";
    }

    /**
     * Shows the form to create a new category.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "create_category";
    }

    /**
     * Processes the form to create a new category.
     */
    @PostMapping
    public String createCategory(@ModelAttribute("category") Category category, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        category.setUser(user);
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    /**
     * Deletes a category.
     */
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
