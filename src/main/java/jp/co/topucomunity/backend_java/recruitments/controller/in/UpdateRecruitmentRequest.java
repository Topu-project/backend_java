package jp.co.topucomunity.backend_java.recruitments.controller.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class UpdateRecruitmentRequest {

    @NotNull(message = "{recruitment.validation.notNull.categories}")
    private RecruitmentCategories recruitmentCategories;

    @NotNull(message = "{recruitment.validation.notNull.progressMethod}")
    private ProgressMethods progressMethods;

    @NotNull(message = "{recruitment.validation.notNull.techStacks}")
    private List<String> techStacks;

    @NotNull(message = "{recruitment.validation.notNull.positions}")
    private List<String> recruitmentPositions;

    @NotNull(message = "{recruitment.validation.notNull.numberOfPeople}")
    private Integer numberOfPeople;

    @NotNull(message = "{recruitment.validation.notNull.progressPeriod}")
    private Integer progressPeriod;

    @NotNull(message = "{recruitment.validation.notNull.deadline}")
    private LocalDate recruitmentDeadline;

    @Email
    @NotBlank(message = "{recruitment.validation.notBlank.contract}")
    private String contract;

    @NotBlank(message = "{recruitment.validation.notBlank.subject}")
    private String subject;

    @NotBlank(message = "{recruitment.validation.notBlank.content}")
    private String content;

}
