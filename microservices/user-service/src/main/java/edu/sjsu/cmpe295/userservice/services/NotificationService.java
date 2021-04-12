package edu.sjsu.cmpe295.userservice.services;

public interface NotificationService {
    void sendNotification(Long toUserId, String type, String title, String content);
}
