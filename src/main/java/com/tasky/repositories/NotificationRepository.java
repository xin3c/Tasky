package com.tasky.repositories;

import com.tasky.models.Notification;
import com.tasky.models.Task;
import com.tasky.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Notification entity.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);

    List<Notification> findByIsSentFalseAndSendTimeBefore(LocalDateTime dateTime);

    // Добавляем метод для поиска уведомления по задаче
    Notification findByTask(Task task);
}