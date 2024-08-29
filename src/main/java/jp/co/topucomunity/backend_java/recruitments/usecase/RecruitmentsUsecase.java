package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.controller.out.RecruitmentResponse;
import jp.co.topucomunity.backend_java.recruitments.domain.*;
import jp.co.topucomunity.backend_java.recruitments.repository.PositionsRepository;
import jp.co.topucomunity.backend_java.recruitments.repository.RecruitmentsRepository;
import jp.co.topucomunity.backend_java.recruitments.repository.TechStacksRepository;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.PostRecruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .map(techName -> techStacksRepository.findByTechnologyName(techName).orElse(TechStack.of(techName)))
                .forEach(techStack -> {
                    var recruitmentTechStack = RecruitmentTechStack.of(techStack, recruitment);
                    recruitmentTechStack.makeRelationship(techStack, recruitment);
                });

        // relationship between recruitment, position, and recruitmentPosition.
        postRecruitment.getRecruitmentPositions().stream()
                .map(positionName -> positionsRepository.findPositionByPositionName(positionName).orElse(Position.of(positionName)))
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
}
