package pl.gdynia.ctm.restctm.exception;

public class APIException {
    private final String message;
    private final Throwable throwable;


    public APIException(String message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;

    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
