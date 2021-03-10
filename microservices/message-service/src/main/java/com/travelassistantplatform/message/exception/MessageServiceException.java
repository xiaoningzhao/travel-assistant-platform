package com.travelassistantplatform.message.exception;

import com.travelassistantplatform.message.enums.ResultEnum;
import lombok.Getter;

@Getter
public class MessageServiceException extends RuntimeException{
    private Integer code;

    public MessageServiceException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public MessageServiceException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
