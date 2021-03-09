package edu.sjsu.cmpe295.notificationservice.controllers;

import edu.sjsu.cmpe295.notificationservice.models.Notification;
import edu.sjsu.cmpe295.notificationservice.models.ResponseMessage;
import edu.sjsu.cmpe295.notificationservice.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Notification", description = "Provide notification service to user.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "Send notification to all users", description = "Send notification to all users", tags = { "Notification" })
    @PostMapping("/send/all")
    public ResponseMessage sendNotificationToAll (@RequestBody Notification notification){
        notificationService.sendNotificationToAll(notification);
        return new ResponseMessage(LocalDateTime.now().toString(), HttpStatus.OK.value(),"Send Successful","");
    }

    @Operation(summary = "Send notification to a specific user", description = "Send notification to a specific user", tags = { "Notification" })
    @PostMapping("/send")
    public ResponseMessage sendNotificationToUser(@RequestBody List<Notification> notifications){
        notifications.forEach(notification -> {
            if(notification.getToUserId()!=null){
                notificationService.sendNotificationToUser(notification.getToUserId(), notification);
            }
        });
        return new ResponseMessage(LocalDateTime.now().toString(), HttpStatus.OK.value(),"Send Successful","");

    }

}
