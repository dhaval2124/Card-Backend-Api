package com.cardmanagement.notification_service.repository;

import com.cardmanagement.notification_service.model.Notification;
import com.cardmanagement.notification_service.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByCustomerIdOrderByCreatedAtDesc(String customerId);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByStatusAndCreatedAtBefore(NotificationStatus status, LocalDateTime dateTime);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.customerId = :customerId AND n.status = :status")
    long countByCustomerIdAndStatus(@Param("customerId") String customerId, @Param("status") NotificationStatus status);

    @Query("SELECT n FROM Notification n WHERE n.recipient = :email AND n.createdAt >= :fromDate")
    List<Notification> findByRecipientAndCreatedAtAfter(@Param("email") String email, @Param("fromDate") LocalDateTime fromDate);
}
