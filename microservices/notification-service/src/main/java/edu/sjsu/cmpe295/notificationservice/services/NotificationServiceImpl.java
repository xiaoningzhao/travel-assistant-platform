package edu.sjsu.cmpe295.notificationservice.services;

import edu.sjsu.cmpe295.notificationservice.exceptions.NotFoundException;
import edu.sjsu.cmpe295.notificationservice.models.Notification;
import edu.sjsu.cmpe295.notificationservice.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/notification";
    private static final String WS_MESSAGE_TRANSFER_INDIVIDUAL_DESTINATION = "/msg";

    @Override
    public void sendNotificationToAll(Notification notification) {
        simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION,
                notification);
    }

    @Override
    public void sendNotificationToUser(String userId, Notification notification) {
        notification.setToUserId(userId);
        notification.setTimestamp(LocalDateTime.now().toString());
        notification.setRead(false);
        notification.setReceived(true);
        notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSendToUser(userId, WS_MESSAGE_TRANSFER_INDIVIDUAL_DESTINATION, notification);
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationByUser(String userId) {
        return notificationRepository.findAllByToUserId(userId);
    }

    @Override
    public Notification getNotificationById(Long id) {
        if(notificationRepository.existsById(id)) {
            return notificationRepository.findById(id).get();
        }else{
            throw new NotFoundException("Cannot find notification");
        }
    }

    @Override
    public void setNotificationAsRead(Long id) {
        if(notificationRepository.existsById(id)){
            Notification notification = notificationRepository.findById(id).get();
            notification.setRead(true);
            notificationRepository.save(notification);
        }else {
            throw new NotFoundException("No such notification");
        }
    }
}
