package jp.co.topucomunity.backend_java.recruitments.controller.out;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *   "error_message": "잘못된 요청입니다",
 *   "validation_errors: {
 *      "title": "1글자 입력해주세요",
 *      "contact": "메일 주소를 제대로 입력해 주세요"
 *   },
 * }
 */

@Getter
public class RecruitmentErrorResponse {

    // TODO : change variable name -> errorMessage
    private final String message;
    private final Map<String, String> validationErrors;

    @Builder
    private RecruitmentErrorResponse(String message, Map<String, String> validationErrors) {
        this.message = message;
        this.validationErrors = validationErrors;
    }

    public static RecruitmentErrorResponse from(String message) {
        return RecruitmentErrorResponse.builder()
                .message(message)
                .validationErrors(new HashMap<>())
                .build();
    }
}
