package jp.co.topucomunity.backend_java.recruitments.controller.out;

import jp.co.topucomunity.backend_java.recruitments.domain.Recruitment;
import jp.co.topucomunity.backend_java.recruitments.domain.RecruitmentPosition;
import jp.co.topucomunity.backend_java.recruitments.domain.RecruitmentTechStack;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RecruitmentIndexPageResponse {
    private final Long id;
    private final RecruitmentCategories recruitmentCategory;
    private final List<String> techStacks;
    private final List<String> positions;
    private final LocalDate recruitmentDeadline;
    private final String subject;
    // TODO: ユーザ情報
    // TODO: View数
    // TODO: 参加人数
    // TODO: isNew いるのか?

    @Builder
    private RecruitmentIndexPageResponse(Long id, RecruitmentCategories recruitmentCategory, List<String> techStacks, List<String> positions, LocalDate recruitmentDeadline, String subject) {
        this.id = id;
        this.recruitmentCategory = recruitmentCategory;
        this.techStacks = techStacks;
        this.positions = positions;
        this.recruitmentDeadline = recruitmentDeadline;
        this.subject = subject;
    }

    public static RecruitmentIndexPageResponse from(Recruitment recruitment) {
        return RecruitmentIndexPageResponse.builder()
                .id(recruitment.getId())
                .recruitmentCategory(recruitment.getRecruitmentCategories())
                .techStacks(recruitment.getRecruitmentTechStacks().stream()
                        .map(RecruitmentTechStack::getTechnologyName).toList())
                .positions(recruitment.getRecruitmentPositions().stream()
                        .map(RecruitmentPosition::getPositionName).toList())
                .recruitmentDeadline(recruitment.getRecruitmentDeadline())
                .subject(recruitment.getSubject())
                .build();
    }
}
