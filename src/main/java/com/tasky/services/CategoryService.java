package com.tasky.services;

import com.tasky.models.Category;
import com.tasky.models.User;
import com.tasky.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for category-related operations.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates or updates a category.
     *
     * @param category the category to save
     * @return the saved category
     */
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Retrieves all categories for a user.
     *
     * @param user the user
     * @return list of categories
     */
    public List<Category> getCategoriesByUser(User user) {
        return categoryRepository.findByUser(user);
    }

    /**
     * Deletes a category.
     *
     * @param categoryId the category ID
     */
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    /**
     * Finds a category by ID.
     *
     * @param categoryId the category ID
     * @return the category
     */
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
