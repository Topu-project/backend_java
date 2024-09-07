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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitmentsUsecase {

    private final RecruitmentsRepository recruitmentsRepository;
    private final TechStacksRepository techStacksRepository;
    private final PositionsRepository positionsRepository;

    @Transactional
    public void post(PostRecruitment postRecruitment) {

        var recruitment = Recruitment.from(postRecruitment);

        var techStacks = Optional.ofNullable(postRecruitment.getTechStacks())
                .orElseThrow(RecruitmentInvalidRequestException::new);

        var positions = Optional.ofNullable(postRecruitment.getRecruitmentPositions())
                .orElseThrow(RecruitmentInvalidRequestException::new);

        // relationship between recruitment, techStack, and recruitmentTechStack.
        techStacks.stream()
                .map(techName -> techStacksRepository.findByTechnologyName(techName).orElse(TechStack.from(techName)))
                .forEach(techStack -> {
                    var recruitmentTechStack = RecruitmentTechStack.of(techStack, recruitment);
                    recruitmentTechStack.makeRelationship(techStack, recruitment);
                });

        // relationship between recruitment, position, and recruitmentPosition.
        positions.stream()
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

        var techStacks = Optional.ofNullable(updateRecruitment.getTechStacks())
                .orElseThrow(RecruitmentInvalidRequestException::new);

        var positions = Optional.ofNullable(updateRecruitment.getRecruitmentPositions())
                .orElseThrow(RecruitmentInvalidRequestException::new);

        recruitment.clearTechStacksAndPositions();

        // relationship between recruitment, techStack, and recruitmentTechStack.
        techStacks.stream()
                .map(techName -> techStacksRepository.findByTechnologyName(techName).orElse(TechStack.from(techName)))
                .forEach(techStack -> {
                    var recruitmentTechStack = RecruitmentTechStack.of(techStack, recruitment);
                    recruitmentTechStack.makeRelationship(techStack, recruitment);
                });

         //relationship between recruitment, position, and recruitmentPosition.
        positions.stream()
                .map(positionName -> positionsRepository.findPositionByPositionName(positionName).orElse(Position.from(positionName)))
                .forEach(position -> {
                    var recruitmentPosition = RecruitmentPosition.of(position, recruitment);
                    recruitmentPosition.makeRelationship(position, recruitment);
                });

        recruitment.update(updateRecruitment);
    }

}
