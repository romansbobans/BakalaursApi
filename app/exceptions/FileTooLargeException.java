package exceptions;

/**
 * Created by TAHKICT on 28/05/14.
 */
public class FileTooLargeException extends Exception {

    public FileTooLargeException() {
    }

    public FileTooLargeException(String message) {
        super(message);
    }

    public FileTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTooLargeException(Throwable cause) {
        super(cause);
    }

    public FileTooLargeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
