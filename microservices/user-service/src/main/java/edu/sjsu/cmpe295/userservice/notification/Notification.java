package edu.sjsu.cmpe295.userservice.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Notification {

    private String toUserId;

    private String type;

    private String title;

    private String content;

}

