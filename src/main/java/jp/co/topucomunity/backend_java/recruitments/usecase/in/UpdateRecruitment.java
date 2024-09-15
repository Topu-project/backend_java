package jp.co.topucomunity.backend_java.recruitments.usecase.in;

import jp.co.topucomunity.backend_java.recruitments.controller.in.UpdateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class UpdateRecruitment {

    private RecruitmentCategories recruitmentCategories;
    private ProgressMethods progressMethods;
    private List<String> techStacks;
    private List<String> recruitmentPositions;
    private int numberOfPeople;
    private int progressPeriod;
    private LocalDate recruitmentDeadline;
    private String contract;
    private String subject;
    private String content;

    public static UpdateRecruitment from(UpdateRecruitmentRequest request) {
        return UpdateRecruitment.builder()
                .recruitmentCategories(request.getRecruitmentCategories())
                .progressMethods(request.getProgressMethods())
                .techStacks(request.getTechStacks())
                .recruitmentPositions(request.getRecruitmentPositions())
                .numberOfPeople(request.getNumberOfPeople())
                .progressPeriod(request.getProgressPeriod())
                .recruitmentDeadline(request.getRecruitmentDeadline())
                .contract(request.getContract())
                .subject(request.getSubject())
                .content(request.getContent())
                .build();
    }
}