package jp.co.topucomunity.backend_java.users.exception;

import org.springframework.http.HttpStatus;

public class UnAuthenticationException extends UsersException {

    private static final String UN_AUTHENTICATION = "인증에 실패 했습니다.";

    public UnAuthenticationException() {
        super(UN_AUTHENTICATION);
    }

    public UnAuthenticationException(Throwable cause) {
        super(UN_AUTHENTICATION, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
