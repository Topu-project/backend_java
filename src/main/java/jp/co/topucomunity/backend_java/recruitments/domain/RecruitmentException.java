package jp.co.topucomunity.backend_java.recruitments.domain;

public abstract class RecruitmentException extends RuntimeException {

    public RecruitmentException() {
        super();
    }

    public RecruitmentException(String message) {
        super(message);
    }

    public RecruitmentException(String message, Throwable cause) {
        super(message, cause);
    }
}