package jp.co.topucomunity.backend_java.recruitments.controller.in;

import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.PostRecruitment;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
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

    public PostRecruitment toPostRecruitment() {
        return PostRecruitment.builder()
                .recruitmentDeadline(this.recruitmentDeadline)
                .progressMethods(this.progressMethods)
                .techStacks(this.techStacks)
                .recruitmentPositions(this.recruitmentPositions)
                .numberOfPeople(this.numberOfPeople)
                .progressPeriod(this.progressPeriod)
                .recruitmentDeadline(this.recruitmentDeadline)
                .contract(this.contract)
                .subject(this.subject)
                .content(this.content)
                .build();
    }
}
