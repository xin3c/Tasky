package com.tasky.repositories;

import com.tasky.models.Notification;
import com.tasky.models.Task;
import com.tasky.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


/**
 * The interface Notification repository.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * Find by user list.
     *
     * @param user the user
     * @return the list
     */
    List<Notification> findByUser(User user);

    /**
     * Find by is sent false and send time before list.
     *
     * @param dateTime the date time
     * @return the list
     */
    List<Notification> findByIsSentFalseAndSendTimeBefore(LocalDateTime dateTime);

    /**
     * Find by task notification.
     *
     * @param task the task
     * @return the notification
     */
    Notification findByTask(Task task);
}
