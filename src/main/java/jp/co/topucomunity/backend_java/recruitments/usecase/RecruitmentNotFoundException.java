package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.domain.RecruitmentException;

public class RecruitmentNotFoundException extends RecruitmentException {
    public RecruitmentNotFoundException() {
        super();
    }

    public RecruitmentNotFoundException(String message) {
        super(message);
    }

    public RecruitmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
