package edu.sjsu.cmpe295.gateway.response;

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

}
