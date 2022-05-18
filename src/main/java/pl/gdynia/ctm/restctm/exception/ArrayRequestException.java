package pl.gdynia.ctm.restctm.exception;

public class ArrayRequestException extends RuntimeException{
    public ArrayRequestException(String message) {
        super(message);
    }

    public ArrayRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
