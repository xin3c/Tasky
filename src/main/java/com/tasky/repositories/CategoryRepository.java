package com.tasky.repositories;

import com.tasky.models.Category;
import com.tasky.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The interface Category repository.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {


    /**
     * Find by user list.
     *
     * @param user the user
     * @return the list
     */
    List<Category> findByUser(User user);
}
