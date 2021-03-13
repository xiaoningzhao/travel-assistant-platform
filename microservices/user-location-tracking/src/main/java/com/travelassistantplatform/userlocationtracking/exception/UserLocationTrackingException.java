package com.travelassistantplatform.userlocationtracking.exception;

import com.travelassistantplatform.userlocationtracking.enums.ResultEnum;
import lombok.Getter;

@Getter
public class UserLocationTrackingException extends RuntimeException{
    private Integer code;

    public UserLocationTrackingException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public UserLocationTrackingException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
