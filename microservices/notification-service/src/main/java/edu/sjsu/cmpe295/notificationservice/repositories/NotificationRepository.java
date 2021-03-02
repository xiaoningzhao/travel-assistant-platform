package edu.sjsu.cmpe295.notificationservice.repositories;

import edu.sjsu.cmpe295.notificationservice.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByToUserId(String userId);
    List<Notification> findAllByToUserIdAndReceivedIsFalse(String userId);
}
