package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import jp.co.topucomunity.backend_java.recruitments.repository.*;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.PostRecruitment;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
@RequiredArgsConstructor
class RecruitmentsUsecaseTest {

    private final RecruitmentsRepository recruitmentsRepository;
    private final TechStacksRepository techStacksRepository;
    private final PositionsRepository positionsRepository;
    private final RecruitmentTechStacksRepository recruitmentTechStacksRepository;
    private final RecruitmentPositionsRepository recruitmentPositionsRepository;
    private final RecruitmentsUsecase recruitmentsUsecase;

    @AfterEach
    void tearDown() {
        recruitmentTechStacksRepository.deleteAllInBatch();
        recruitmentPositionsRepository.deleteAllInBatch();
        positionsRepository.deleteAllInBatch();
        techStacksRepository.deleteAllInBatch();
        recruitmentsRepository.deleteAllInBatch();
    }

    @DisplayName("응모글을 작성하면 응모글 목록에 담긴다.")
    @Test
    void post() {

        // given
        PostRecruitment postRecruitment = createPostRecruitment();

        // when
        recruitmentsUsecase.post(postRecruitment);

        // then
        Assertions.assertThat(recruitmentsRepository.count()).isEqualTo(1);
        var recruitment = recruitmentsRepository.findAll().getFirst();
        Assertions.assertThat(recruitment).isNotNull();

        Assertions.assertThat(recruitment.getRecruitmentCategories()).isEqualTo(RecruitmentCategories.STUDY);
        Assertions.assertThat(recruitment.getProgressMethods()).isEqualTo(ProgressMethods.ONLINE);
        Assertions.assertThat(recruitment.getRecruitmentTechStacks())
                .extracting("technologyName")
                .containsExactly("Java", "Spring");
        Assertions.assertThat(recruitment.getRecruitmentPositions())
                .extracting("positionName")
                .containsExactly("Backend", "Frontend");
        Assertions.assertThat(recruitment.getNumberOfPeople()).isEqualTo(9);
        Assertions.assertThat(recruitment.getProgressPeriod()).isEqualTo(10);
        Assertions.assertThat(recruitment.getRecruitmentDeadline()).isEqualTo(LocalDate.of(2024, 10, 30));
        Assertions.assertThat(recruitment.getContract()).isEqualTo("test_test@naver.com");
        Assertions.assertThat(recruitment.getSubject()).isEqualTo("첫 제목");
        Assertions.assertThat(recruitment.getContent()).isEqualTo("첫 본문");

    }

    private static PostRecruitment createPostRecruitment() {
        return PostRecruitment.builder()
                .recruitmentCategories(RecruitmentCategories.STUDY)
                .progressMethods(ProgressMethods.ONLINE)
                .techStacks(List.of("Java","Spring"))
                .recruitmentPositions(List.of("Backend", "Frontend"))
                .numberOfPeople(9)
                .progressPeriod(10)
                .contract("test_test@naver.com")
                .subject("첫 제목")
                .content("첫 본문")
                .recruitmentDeadline(LocalDate.of(2024, 10, 30))
                .build();
    }

    // TODO
    //  @DisplayName("응모글을 작성에 실패한다")

}