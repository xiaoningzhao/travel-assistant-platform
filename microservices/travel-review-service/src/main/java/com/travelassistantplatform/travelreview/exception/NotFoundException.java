package com.travelassistantplatform.travelreview.exception;

import com.travelassistantplatform.travelreview.enums.ResultEnum;

public class NotFoundException extends TravelReviewException {

    public NotFoundException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public NotFoundException(Integer code, String message) {
        super(code, message);
    }
}
