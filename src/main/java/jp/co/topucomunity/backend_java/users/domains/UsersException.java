package jp.co.topucomunity.backend_java.users.domains;

public abstract class UsersException extends RuntimeException {

    public UsersException() {
    }

    public UsersException(String message) {
        super(message);
    }

    public UsersException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
