package com.tasky.controllers;

import com.tasky.models.Category;
import com.tasky.models.User;
import com.tasky.services.CategoryService;
import com.tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public String listCategories(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Category> categories = categoryService.getCategoriesByUser(user);
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "create_category";
    }

    @PostMapping
    public String createCategory(@ModelAttribute Category category, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        category.setUser(user);
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        Category category = categoryService.findCategoryById(id);
        if (category == null || !category.getUser().equals(user)) {
            return "redirect:/categories";
        }
        model.addAttribute("category", category);
        return "edit_category";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute Category category, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        category.setUser(user);
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        Category category = categoryService.findCategoryById(id);
        if (category != null && category.getUser().equals(user)) {
            categoryService.deleteCategory(id);
        }
        return "redirect:/categories";
    }
}
