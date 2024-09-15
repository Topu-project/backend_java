package jp.co.topucomunity.backend_java.users.exception;

public abstract class UserException extends RuntimeException {

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
