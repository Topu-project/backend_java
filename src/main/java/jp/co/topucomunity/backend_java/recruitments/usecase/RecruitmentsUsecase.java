package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.controller.out.RecruitmentIndexPageResponse;
import jp.co.topucomunity.backend_java.recruitments.controller.out.RecruitmentResponse;
import jp.co.topucomunity.backend_java.recruitments.domain.*;
import jp.co.topucomunity.backend_java.recruitments.repository.PositionsRepository;
import jp.co.topucomunity.backend_java.recruitments.repository.RecruitmentsRepository;
import jp.co.topucomunity.backend_java.recruitments.repository.TechStacksRepository;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.PostRecruitment;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.UpdateRecruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentsUsecase {

    private final RecruitmentsRepository recruitmentsRepository;
    private final TechStacksRepository techStacksRepository;
    private final PositionsRepository positionsRepository;

    @Transactional
    public void post(PostRecruitment postRecruitment) {

        var recruitment = Recruitment.from(postRecruitment);

        // relationship between recruitment, techStack, and recruitmentTechStack.
        postRecruitment.getTechStacks().stream()
                .map(techName -> techStacksRepository.findByTechnologyName(techName).orElse(TechStack.from(techName)))
                .forEach(techStack -> {
                    var recruitmentTechStack = RecruitmentTechStack.of(techStack, recruitment);
                    recruitmentTechStack.makeRelationship(techStack, recruitment);
                });

        // relationship between recruitment, position, and recruitmentPosition.
        postRecruitment.getRecruitmentPositions().stream()
                .map(positionName -> positionsRepository.findPositionByPositionName(positionName).orElse(Position.from(positionName)))
                .forEach(position -> {
                    var recruitmentPosition = RecruitmentPosition.of(position, recruitment);
                    recruitmentPosition.makeRelationship(position, recruitment);
                });

        recruitmentsRepository.save(recruitment);
    }

    public RecruitmentResponse getRecruitment(Long recruitmentId) {
        var foundRecruitment = recruitmentsRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);
        return RecruitmentResponse.from(foundRecruitment);
    }

    public void deleteRecruitment(Long recruitmentId) {
        recruitmentsRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        recruitmentsRepository.deleteById(recruitmentId);
    }

    public List<RecruitmentIndexPageResponse> getRecruitments() {
        var recruitments = recruitmentsRepository.findAll();

        return recruitments.stream()
                .map(RecruitmentIndexPageResponse::from).toList();
    }

    @Transactional
    public void update(Long recruitmentId, UpdateRecruitment updateRecruitment) {

        var recruitment = recruitmentsRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        UpdateRecruitment.UpdateRecruitmentBuilder updateRecruitmentBuilder = recruitment.toEditor();

        UpdateRecruitment updatedRecruitment = updateRecruitmentBuilder
                .recruitmentCategories(updateRecruitment.getRecruitmentCategories())
                .progressMethods(updateRecruitment.getProgressMethods())
                .numberOfPeople(updateRecruitment.getNumberOfPeople())
                .progressPeriod(updateRecruitment.getProgressPeriod())
                .recruitmentDeadline(updateRecruitment.getRecruitmentDeadline())
                .contract(updateRecruitment.getContract())
                .subject(updateRecruitment.getSubject())
                .content(updateRecruitment.getContent())
                .build();

        recruitment.update(updatedRecruitment);

        // relationship between recruitment, techStack, and recruitmentTechStack.
        updateRecruitment.getTechStacks().stream()
                .map(techName -> techStacksRepository.findByTechnologyName(techName).orElse(TechStack.from(techName)))
                .forEach(techStack -> {
                    var recruitmentTechStack = RecruitmentTechStack.of(techStack, recruitment);
                    recruitmentTechStack.makeRelationship(techStack, recruitment);
                });

        // relationship between recruitment, position, and recruitmentPosition.
        updateRecruitment.getRecruitmentPositions().stream()
                .map(positionName -> positionsRepository.findPositionByPositionName(positionName).orElse(Position.from(positionName)))
                .forEach(position -> {
                    var recruitmentPosition = RecruitmentPosition.of(position, recruitment);
                    recruitmentPosition.makeRelationship(position, recruitment);
                });
    }

}
