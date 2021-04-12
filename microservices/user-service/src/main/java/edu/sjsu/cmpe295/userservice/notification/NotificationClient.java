package edu.sjsu.cmpe295.userservice.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/notification/send", produces = "application/json")
    void sendNotification(@RequestBody List<Notification> notifications);
}
