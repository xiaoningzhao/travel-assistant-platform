package com.travelassistantplatform.message.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"Success"),
    /** not found error messages */
    USER_HAS_NO_CHAT_HISTORY(1,"User has no chat history."),
    MESSAGE_NOT_FOUND(2,"Message id not found."),
    CHAT_NOT_FOUND(3,"Chat id not found."),
    /** saving failure error messages */
    MESSAGE_SAVING_FAILED(4,"Failed to save message");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
