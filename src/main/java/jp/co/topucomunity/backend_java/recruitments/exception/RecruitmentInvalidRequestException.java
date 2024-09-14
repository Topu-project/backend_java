package jp.co.topucomunity.backend_java.recruitments.exception;

import org.springframework.http.HttpStatus;

public class RecruitmentInvalidRequestException extends RecruitmentException {

    private static final String INVALID_REQUEST = "모든 항목을 입력해야합니다.";

    public RecruitmentInvalidRequestException() {
        super(INVALID_REQUEST);
    }

    public RecruitmentInvalidRequestException(Throwable cause) {
        super(INVALID_REQUEST, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}

