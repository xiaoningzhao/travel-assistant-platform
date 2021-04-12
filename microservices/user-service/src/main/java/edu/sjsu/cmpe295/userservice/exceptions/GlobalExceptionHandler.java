package edu.sjsu.cmpe295.userservice.exceptions;

import edu.sjsu.cmpe295.userservice.models.ResponseMessage;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleException(Exception e){
        return new ResponseMessage(LocalDateTime.now().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getClass().getSimpleName(), getRealMessage(e));
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

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleFileException(Exception e){
        return new ResponseMessage(LocalDateTime.now().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, InvalidDataAccessApiUsageException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handleMethodArgumentNotValidException(Exception e){
        return new ResponseMessage(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST.value(), e.getClass().getSimpleName(), e.getMessage());
    }

    private String getRealMessage(Throwable e) {
        while (e != null) {
            Throwable cause = e.getCause();
            if (cause == null) {
                return e.getMessage();
            }
            e = cause;
        }
        return "";
    }
}
