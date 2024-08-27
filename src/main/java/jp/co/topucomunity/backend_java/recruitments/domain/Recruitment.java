package jp.co.topucomunity.backend_java.recruitments.domain;

import jakarta.persistence.*;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RecruitmentCategories recruitmentCategories;

    @Enumerated(EnumType.STRING)
    private ProgressMethods progressMethods;

    private int numberOfPeople;
    private int progressPeriod;
    private LocalDate recruitmentDeadline;
    private String contract;
    private String subject;

    @Lob
    private String content;

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitmentTechStack> recruitmentTechStacks = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitmentPosition> recruitmentPositions = new ArrayList<>();

    @Builder
    public Recruitment(RecruitmentCategories recruitmentCategories, ProgressMethods progressMethods, int numberOfPeople, int progressPeriod, LocalDate recruitmentDeadline, String contract, String subject, String content, List<RecruitmentTechStack> recruitmentTechStacks) {
        this.recruitmentCategories = recruitmentCategories;
        this.progressMethods = progressMethods;
        this.numberOfPeople = numberOfPeople;
        this.progressPeriod = progressPeriod;
        this.recruitmentDeadline = recruitmentDeadline;
        this.contract = contract;
        this.subject = subject;
        this.content = content;
        this.recruitmentTechStacks = recruitmentTechStacks;
    }

    public void makeRelationshipWithRecruitmentTechStack(RecruitmentTechStack recruitmentTechStack) {
        this.recruitmentTechStacks.add(recruitmentTechStack);
    }

    public void makeRelationshipWithRecruitmentPosition(RecruitmentPosition recruitmentPosition) {
        this.recruitmentPositions.add(recruitmentPosition);
    }
}
