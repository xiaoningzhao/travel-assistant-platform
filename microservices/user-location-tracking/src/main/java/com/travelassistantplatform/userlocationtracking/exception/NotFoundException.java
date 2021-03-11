package com.travelassistantplatform.userlocationtracking.exception;

import com.travelassistantplatform.userlocationtracking.enums.ResultEnum;

public class NotFoundException extends UserLocationTrackingException{
    public NotFoundException(ResultEnum resultEnum){
        super(resultEnum);
    }

    public NotFoundException(Integer code, String message){
        super(code, message);
    }
}
