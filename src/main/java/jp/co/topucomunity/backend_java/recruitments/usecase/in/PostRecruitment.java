package jp.co.topucomunity.backend_java.recruitments.usecase.in;

import jp.co.topucomunity.backend_java.recruitments.domain.Recruitment;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class PostRecruitment {

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

    public Recruitment toRecruitment() {
        return Recruitment.builder()
                .recruitmentCategories(this.recruitmentCategories)
                .progressMethods(this.progressMethods)
                .recruitmentTechStacks(new ArrayList<>())
                .numberOfPeople(this.numberOfPeople)
                .progressPeriod(this.progressPeriod)
                .recruitmentDeadline(this.recruitmentDeadline)
                .contract(this.contract)
                .subject(this.subject)
                .content(this.content)
                .build();
    }
}
