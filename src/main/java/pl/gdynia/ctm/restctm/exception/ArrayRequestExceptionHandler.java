package pl.gdynia.ctm.restctm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArrayRequestExceptionHandler {
    @ExceptionHandler(value = {ArrayRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ArrayRequestException exception) {
        var ep = new APIException(exception.getMessage(), exception, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ep, HttpStatus.BAD_REQUEST);
    }

}
