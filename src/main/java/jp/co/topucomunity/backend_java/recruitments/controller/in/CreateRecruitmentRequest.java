package jp.co.topucomunity.backend_java.recruitments.controller.in;

import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CreateRecruitmentRequest {

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

}
