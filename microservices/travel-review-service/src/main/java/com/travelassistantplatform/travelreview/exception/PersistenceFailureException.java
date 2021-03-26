package com.travelassistantplatform.travelreview.exception;

import com.travelassistantplatform.travelreview.enums.ResultEnum;

public class PersistenceFailureException extends TravelReviewException {
    public PersistenceFailureException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public PersistenceFailureException(Integer code, String message) {
        super(code, message);
    }
}
