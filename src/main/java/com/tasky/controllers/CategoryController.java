package com.tasky.controllers;

import com.tasky.models.Category;
import com.tasky.models.User;
import com.tasky.services.CategoryService;
import com.tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tasky.services.NotificationService.loggerMan;


/**
 * The type Category controller.
 */
@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;
    @Autowired
    private ResourceLoader resourceLoader;
    /**
     * List categories string.
     *
     * @param model       the model
     * @param userDetails the user details
     * @return the string
     */
    @GetMapping
    public String listCategories(final Model model, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        final List<Category> categories = categoryService.getCategoriesByUser(user);
        model.addAttribute("light_icons", loadIcons("classpath:/static/icons/light/*.svg"));
        model.addAttribute("dark_icons", loadIcons("classpath:/static/icons/dark/*.svg"));
        model.addAttribute("categories", categories);
        return "categories";
    }

    /**
     * Show create form string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/new")
    public String showCreateCategoryForm(Model model) {

        model.addAttribute("light_icons", loadIcons("classpath:/static/icons/light/*.svg"));
        model.addAttribute("dark_icons", loadIcons("classpath:/static/icons/dark/*.svg"));

        model.addAttribute("category", new Category());
        return "create_category";
    }

    public static List<String> loadIcons(String pathPattern) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
        try {
            resources = resolver.getResources(pathPattern);
        } catch (IOException e) {
            resources = new Resource[0];
            loggerMan.error("Entering NotificationService.void sendNotification with args: final Notification notification");
        }
        return Stream.of(resources)
                .map(Resource::getFilename)
                .collect(Collectors.toList());
    }

    /**
     * Create category string.
     *
     * @param category    the category
     * @param userDetails the user details
     * @return the string
     */
    @PostMapping
    public String createCategory(@ModelAttribute final Category category, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        category.setUser(user);
        categoryService.saveCategory(category);
        return "redirect:/categories";
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
        final Category category = categoryService.findCategoryById(id);
        if (category == null || !category.getUser().equals(user)) {
            return "redirect:/categories";
        }
        model.addAttribute("category", category);
        return "edit_category";
    }

    /**
     * Update category string.
     *
     * @param category    the category
     * @param userDetails the user details
     * @return the string
     */
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute final Category category, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        category.setUser(user);
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    /**
     * Delete category string.
     *
     * @param id          the id
     * @param userDetails the user details
     * @return the string
     */
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable final Long id, @AuthenticationPrincipal final UserDetails userDetails) {
        final User user = userService.findUserByUsername(userDetails.getUsername());
        final Category category = categoryService.findCategoryById(id);
        if (category != null && category.getUser().equals(user)) {
            categoryService.deleteCategory(id);
        }
        return "redirect:/categories";
    }
}
