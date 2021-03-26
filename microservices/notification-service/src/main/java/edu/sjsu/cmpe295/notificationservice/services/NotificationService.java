package edu.sjsu.cmpe295.notificationservice.services;

import edu.sjsu.cmpe295.notificationservice.models.Notification;

import java.util.List;

public interface NotificationService {
    void sendNotificationToAll(Notification notification);
    void sendNotificationToUser(String userId, Notification notification);
    Notification saveNotification(Notification notification);
    List<Notification> getNotificationByUser(String userId);
    Notification getNotificationById(Long id);
    void setNotificationAsRead(Long id);
}
