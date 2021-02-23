package edu.sjsu.cmpe295.userservice.exceptions;

import edu.sjsu.cmpe295.userservice.models.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleException(Exception e){
        return new ResponseMessage(LocalDateTime.now().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseMessage handleConflictException(Exception e){
        return new ResponseMessage(LocalDateTime.now().toString(), HttpStatus.CONFLICT.value(), e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage handleNotFoundException(Exception e){
        return new ResponseMessage(LocalDateTime.now().toString(), HttpStatus.NOT_FOUND.value(), e.getClass().getSimpleName(), e.getMessage());
    }
}
