package com.tasky.repositories;

import com.tasky.models.UserSub;
import com.tasky.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface User subscription repository.
 */
@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSub, Long> {
    /**
     * Find by user list.
     *
     * @param user the user
     * @return the list
     */
    List<UserSub> findByUser(User user);
}
