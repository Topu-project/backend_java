package jp.co.topucomunity.backend_java.recruitments.controller.out;

import jp.co.topucomunity.backend_java.recruitments.domain.Recruitment;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RecruitmentResponse {
    private Long id;
    private RecruitmentCategories recruitmentCategories;
    private ProgressMethods progressMethods;
    private int numberOfPeople;
    private int progressPeriod;
    private LocalDate recruitmentDeadline;
    private String contract;
    private String subject;
    private String content;
    private List<String> techStacks = new ArrayList<>();
    private List<String> positions = new ArrayList<>();

    @Builder
    private RecruitmentResponse(Long id, RecruitmentCategories recruitmentCategories, ProgressMethods progressMethods, int numberOfPeople, int progressPeriod, LocalDate recruitmentDeadline, String contract, String subject, String content, List<String> techStacks, List<String> positions) {
        this.id = id;
        this.recruitmentCategories = recruitmentCategories;
        this.progressMethods = progressMethods;
        this.numberOfPeople = numberOfPeople;
        this.progressPeriod = progressPeriod;
        this.recruitmentDeadline = recruitmentDeadline;
        this.contract = contract;
        this.subject = subject;
        this.content = content;
        this.techStacks = techStacks;
        this.positions = positions;
    }

    public static RecruitmentResponse from(Recruitment recruitment) {
        return RecruitmentResponse.builder()
                .id(recruitment.getId())
                .recruitmentCategories(recruitment.getRecruitmentCategories())
                .progressMethods(recruitment.getProgressMethods())
                .numberOfPeople(recruitment.getNumberOfPeople())
                .progressPeriod(recruitment.getProgressPeriod())
                .recruitmentDeadline(recruitment.getRecruitmentDeadline())
                .contract(recruitment.getContract())
                .subject(recruitment.getSubject())
                .content(recruitment.getContent())
                .techStacks(recruitment.getRecruitmentTechStacks().stream()
                        .map(recruitmentTechStack ->
                                recruitmentTechStack.getTechStack()
                                        .getTechnologyName())
                        .toList())
                .positions(recruitment.getRecruitmentPositions().stream()
                        .map(recruitmentPosition ->
                                recruitmentPosition.getPosition()
                                        .getPositionName())
                        .toList())
                .build();
    }
}
