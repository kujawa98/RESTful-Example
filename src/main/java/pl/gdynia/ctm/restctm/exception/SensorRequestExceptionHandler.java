package pl.gdynia.ctm.restctm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SensorRequestExceptionHandler {
    @ExceptionHandler(value = {SensorRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(SensorRequestException exception) {
        var apiException = new APIException(exception.getMessage(), exception);
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
