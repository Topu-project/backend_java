package jp.co.topucomunity.backend_java.users.usecases;

import jp.co.topucomunity.backend_java.users.domains.UsersException;
import org.springframework.http.HttpStatus;

public class UnAuthenticationException extends UsersException {

    private static final String UNAUTHENTICATION = "인증에 실패 했습니다.";

    public UnAuthenticationException() {
        super(UNAUTHENTICATION);
    }

    public UnAuthenticationException(Throwable cause) {
        super(UNAUTHENTICATION, cause);
    }

    @Override
  public int getStatusCode() {
    return HttpStatus.UNAUTHORIZED.value();
  }
}
