package msa.customer.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "msa.customer")
public class ExceptionControllerAdvice {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult nullPointerExceptionHandler(NullPointerException e){
        return new ErrorResult("NULL_POINTER", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e){
        return new ErrorResult("ILLEGAL_ARGUMENT", e.getMessage());
    }

    @ExceptionHandler(IllegalCallerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult illegalCallerException(IllegalCallerException e){
        return new ErrorResult("ILLEGAL_CALLER", e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult runtimeExceptionHandler(RuntimeException e){
        return new ErrorResult("RUNTIME", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult exceptionHandler(Exception e){
        return new ErrorResult("INTERNAL_SERVER", e.getMessage());
    }

}
