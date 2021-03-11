package com.travelassistantplatform.userlocationtracking.exception;

import com.travelassistantplatform.userlocationtracking.enums.ResultEnum;

public class PersistenceFailureException extends UserLocationTrackingException{
    public PersistenceFailureException(ResultEnum resultEnum){
        super(resultEnum);
    }

    public PersistenceFailureException(Integer code, String message){
        super(code, message);
    }
}
