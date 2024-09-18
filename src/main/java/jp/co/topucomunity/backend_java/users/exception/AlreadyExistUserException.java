package jp.co.topucomunity.backend_java.users.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistUserException extends UserException {

    private static final String MESSAGE = "이미 등록된 유저입니다.";

    public AlreadyExistUserException() {
        super(MESSAGE);
    }

    public AlreadyExistUserException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
