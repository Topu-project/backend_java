package jp.co.topucomunity.backend_java.recruitments.controller;

import jp.co.topucomunity.backend_java.recruitments.controller.out.RecruitmentErrorResponse;
import jp.co.topucomunity.backend_java.recruitments.exception.RecruitmentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RecruitmentException.class)
    public ResponseEntity<RecruitmentErrorResponse> recruitmentExceptionHandler(RecruitmentException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(RecruitmentErrorResponse.from(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RecruitmentErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        RecruitmentErrorResponse errorResponse = RecruitmentErrorResponse.from(e.getMessage());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorResponse;
    }
}
