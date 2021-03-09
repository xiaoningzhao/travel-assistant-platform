package com.travelassistantplatform.travelreview.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"Success"),
    /** not found error messages */
    USER_HAS_NO_TRIP_HISTORY(1,"User has no trip history."),
    TRIP_NOT_FOUND(2,"Trip id not found."),
    DESTINATION_NOT_FOUND(3,"Destination id not found."),
    /** saving failure error messages */
    USER_TRIP_HISTORY_SAVING_FAILED(4,"Failed to save user trip history"),
    TRIP_SAVING_FAILED(5,"Failed to save trip."),
    DESTINATION_SAVING_FAILED(6,"Failed to save destination.");


    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
