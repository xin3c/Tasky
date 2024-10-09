package com.tasky.repositories;

import com.tasky.models.Category;
import com.tasky.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds categories by user.
     *
     * @param user the user
     * @return list of categories
     */
    List<Category> findByUser(User user);
}
