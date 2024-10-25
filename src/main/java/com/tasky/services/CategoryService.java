package com.tasky.services;

import com.tasky.models.Category;
import com.tasky.models.User;
import com.tasky.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The type Category service.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;


    /**
     * Save category.
     *
     * @param category the category
     */
    public void saveCategory(final Category category) {
        categoryRepo.save(category);
    }


    /**
     * Gets categories by user.
     *
     * @param user the user
     * @return the categories by user
     */
    public List<Category> getCategoriesByUser(final User user) {
        return categoryRepo.findByUser(user);
    }


    /**
     * Delete category.
     *
     * @param categoryId the category id
     */
    public void deleteCategory(final Long categoryId) {
        categoryRepo.deleteById(categoryId);
    }


    /**
     * Find category by id category.
     *
     * @param categoryId the category id
     * @return the category
     */
    public Category findCategoryById(final Long categoryId) {
        return categoryRepo.findById(categoryId).orElse(null);
    }
}
