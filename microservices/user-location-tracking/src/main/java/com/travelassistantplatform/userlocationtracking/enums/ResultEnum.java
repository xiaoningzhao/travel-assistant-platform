package com.travelassistantplatform.userlocationtracking.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"Success"),
    /** not found error messages */
    USER_HAS_NO_LOCATION_RECORDS(1,"User has no chat history."),
    /** saving failure error messages */
    USER_LOCATION_SAVING_FAILED(2,"Failed to save user location.");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

