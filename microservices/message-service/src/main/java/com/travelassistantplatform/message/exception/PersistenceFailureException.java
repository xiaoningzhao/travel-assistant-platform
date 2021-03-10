package com.travelassistantplatform.message.exception;

import com.travelassistantplatform.message.enums.ResultEnum;

public class PersistenceFailureException extends MessageServiceException{
    public PersistenceFailureException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public PersistenceFailureException(Integer code, String message) {
        super(code, message);
    }
}
