package edu.sjsu.cmpe295.notificationservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseMessage {
    private String timestamp;
    private Integer status;
    private String title;
    private String message;

    public ResponseMessage() {

    }
}
