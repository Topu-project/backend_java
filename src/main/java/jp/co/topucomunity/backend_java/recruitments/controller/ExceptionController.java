package jp.co.topucomunity.backend_java.recruitments.controller;

import jp.co.topucomunity.backend_java.recruitments.controller.out.RecruitmentErrorResponse;
import jp.co.topucomunity.backend_java.recruitments.domain.RecruitmentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RecruitmentException.class)
    public ResponseEntity<RecruitmentErrorResponse> recruitmentExceptionHandler(RecruitmentException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(RecruitmentErrorResponse.from(e.getMessage()));
    }
}
