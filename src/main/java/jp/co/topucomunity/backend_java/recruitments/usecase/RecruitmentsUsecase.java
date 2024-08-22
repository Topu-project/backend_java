package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.domain.Position;
import jp.co.topucomunity.backend_java.recruitments.domain.RecruitmentPosition;
import jp.co.topucomunity.backend_java.recruitments.domain.RecruitmentTechStack;
import jp.co.topucomunity.backend_java.recruitments.domain.TechStack;
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

        var recruitment = postRecruitment.toRecruitment();

        // relationship between recruitment, techStack, and recruitmentTechStack.
        postRecruitment.getTechStacks().stream()
                .map(techName -> techStacksRepository.findByTechnologyName(techName).orElse(TechStack.of(techName)))
                .forEach(techStack -> {
                    var recruitmentTechStack = RecruitmentTechStack.from(techStack, recruitment);
                    recruitmentTechStack.makeRelationship(techStack, recruitment);
                });

        // relationship between recruitment, position, and recruitmentPosition.
        postRecruitment.getRecruitmentPositions().stream()
                .map(positionName -> positionsRepository.findByPosition(positionName).orElse(Position.of(positionName)))
                .forEach(position -> {
                    var recruitmentPosition = RecruitmentPosition.from(position, recruitment);
                    recruitmentPosition.makeRelationship(position, recruitment);
                });

        recruitmentsRepository.save(recruitment);
    }

}
