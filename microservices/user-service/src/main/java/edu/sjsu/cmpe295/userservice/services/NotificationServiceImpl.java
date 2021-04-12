package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.notification.Notification;
import edu.sjsu.cmpe295.userservice.notification.NotificationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationClient notificationClient;

    @Async
    @Override
    public void sendNotification(Long toUserId, String type, String title, String content) {

        try {
            List<Notification> notifications = new ArrayList<>();
            notifications.add(new Notification(toUserId.toString(), type, title, content));
            notificationClient.sendNotification(notifications);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
