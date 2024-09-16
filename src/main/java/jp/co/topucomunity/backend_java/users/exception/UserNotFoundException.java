package jp.co.topucomunity.backend_java.users.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {

    private static final String MESSAGE = "해당하는 유저가 존재하지 않습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
