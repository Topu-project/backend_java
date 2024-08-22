package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.domain.RecruitmentTechStack;
import jp.co.topucomunity.backend_java.recruitments.domain.TechStack;
import jp.co.topucomunity.backend_java.recruitments.repository.RecruitmentsRepository;
import jp.co.topucomunity.backend_java.recruitments.repository.TechStackRepository;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.PostRecruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentsUsecase {

    private final RecruitmentsRepository recruitmentsRepository;
    private final TechStackRepository techStackRepository;

    @Transactional
    public void post(PostRecruitment postRecruitment) {

        var recruitment = postRecruitment.toRecruitment();
        var recruitmentTechStacks = postRecruitment.getTechStacks().stream()
                .map(techName -> techStackRepository.findByTechnologyName(techName).orElse(TechStack.of(techName)))
                .map(techStack -> {
                    var recruitmentTechStack = RecruitmentTechStack.from(techStack, recruitment);
                    techStack.addRecruitmentTechStack(recruitmentTechStack);
                    return recruitmentTechStack;
                })
                .toList();

        recruitment.relateRecruitmentTechStack(recruitmentTechStacks);

        recruitmentsRepository.save(recruitment);
    }

}
