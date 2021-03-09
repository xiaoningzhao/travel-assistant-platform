package com.travelassistantplatform.travelreview.exception;

import com.travelassistantplatform.travelreview.enums.ResultEnum;
import lombok.Getter;

@Getter
public class TravelReviewException extends RuntimeException{
    private Integer code;

    public TravelReviewException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public TravelReviewException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
