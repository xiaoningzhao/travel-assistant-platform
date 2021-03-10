package edu.sjsu.cmpe295.notificationservice.services;

import edu.sjsu.cmpe295.notificationservice.models.Notification;

import java.util.HashMap;
import java.util.List;

public interface NotificationService {
    void sendNotificationToAll(Notification notification);
    void sendNotificationToUser(String userId, Notification notification);
    void sendMissedNotificationToUser(String userId);
    Notification saveNotification(Notification notification);
    List<Notification> getNotificationByUser(String userId);
    Notification getNotificationById(Long id);
    HashMap<String, String> getConnectedUsers();
    void addConnectedUser(String userId, String sessionId);
    void removeConnectedUser(String userId);
    void setNotificationAsRead(Long id);
}
