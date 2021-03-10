package com.travelassistantplatform.message.exception;

import com.travelassistantplatform.message.enums.ResultEnum;

public class NotFoundException extends MessageServiceException{
    public NotFoundException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public NotFoundException(Integer code, String message) {
        super(code, message);
    }
}
