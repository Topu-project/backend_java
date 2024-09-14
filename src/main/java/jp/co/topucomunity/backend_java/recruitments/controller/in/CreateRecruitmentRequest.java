package jp.co.topucomunity.backend_java.recruitments.controller.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CreateRecruitmentRequest {

    @NotNull(message = "카테고리를 선택해 주세요.")
    private RecruitmentCategories recruitmentCategories;

    @NotNull(message = "진행방법을 선택해 주세요.")
    private ProgressMethods progressMethods;

    @Size(min = 1, message = "기술스택을 입력해 주세요.")
    private List<String> techStacks;

    @Size(min = 1, message = "응모 포지션을 선택해 주세요.")
    private List<String> recruitmentPositions;

    @NotNull(message = "모집 인원을 입력해 주세요.")
    private Integer numberOfPeople;

    @NotNull(message = "진행 기간을 입력해 주세요.")
    private Integer progressPeriod;

    @NotNull(message = "마감일을 입력해 주세요.")
    private LocalDate recruitmentDeadline;

    @Email
    @NotBlank(message = "연락처를 입력해 주세요.")
    private String contract;

    @NotBlank(message = "제목을 입력해 주세요.")
    private String subject;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

}
