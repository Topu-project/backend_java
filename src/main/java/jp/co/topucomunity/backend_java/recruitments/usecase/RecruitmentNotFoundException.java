package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.domain.RecruitmentException;
import org.springframework.http.HttpStatus;

public class RecruitmentNotFoundException extends RecruitmentException {

    public static final String RECRUITMENT_NOT_FOUND = "응모글을 찾을 수 없습니다.";

    public RecruitmentNotFoundException() {
        super(RECRUITMENT_NOT_FOUND);
    }

    public RecruitmentNotFoundException(Throwable cause) {
        super(RECRUITMENT_NOT_FOUND, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
