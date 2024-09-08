package jp.co.topucomunity.backend_java.recruitments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.topucomunity.backend_java.recruitments.controller.in.CreateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.controller.in.UpdateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.domain.*;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import jp.co.topucomunity.backend_java.recruitments.repository.*;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.UpdateRecruitment;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
@SpringBootTest
@RequiredArgsConstructor
class RecruitmentsControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final RecruitmentsRepository recruitmentsRepository;
    private final TechStacksRepository techStacksRepository;
    private final PositionsRepository positionsRepository;
    private final RecruitmentTechStacksRepository recruitmentTechStacksRepository;
    private final RecruitmentPositionsRepository recruitmentPositionsRepository;

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
    void postRecruitment() throws Exception {

        // given
        var request = CreateRecruitmentRequest.builder()
                .recruitmentCategories(RecruitmentCategories.STUDY)
                .progressMethods(ProgressMethods.ALL)
                .techStacks(List.of("Java", "Spring", "JPA"))
                .recruitmentPositions(List.of("Backend", "DevOps", "Infra"))
                .numberOfPeople(3)
                .progressPeriod(6)
                .recruitmentDeadline(LocalDate.of(2024, 10, 30))
                .contract("test@tesc.om")
                .subject("새로운 서비스를 개발하실 분을 모집합니다.")
                .content("현실은 SI, SES, 개레거시지롱")
                .build();
        var jsonString = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/recruitments")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    // @DisplayName("필수 항목들을 입력하지 않으면 응모글을 작성할 수 없다.")
    // TODO : 응모글 작성 실패 케이스 작성

    // @DisplayName("응모글을 작성할 때 포지션을 선택해서 등록하면 포지션도 같이 등록 된다.")
    // TODO : 포지션 등록 확인

    // @DisplayName("응모글을 작성할 때 포지션을 선택해서 등록하면 이미 등록된 포지션일 경우 중복 등록되지 않는다.")
    // TODO : 포지션 중복 등록 확인

    // @DisplayName("응모글을 작성할 때 기술스택을 선택해서 등록하면 기술스택도 같이 등록 된다.")
    // TODO : 기술스택 등록 확인

    // @DisplayName("응모글을 작성에서 기술스택을 선택해서 등록할 때 이미 등록된 기술스택일 경우 중복 등록되지 않는다.")
    // TODO : 기술스택 중복 등록 확인

    @Transactional
    @DisplayName("응모ID로 해당 응모 상세페이지를 조회 할 수 있다.")
    @Test
    void getRecruitmentById() throws Exception {
        // given
        var recruitment = createRecruitment();

        var savedRecruitment = recruitmentsRepository.save(recruitment);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/recruitments/{recruitmentId}", savedRecruitment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id", is(savedRecruitment.getId().intValue())),
                        jsonPath("$.recruitmentCategories", is("STUDY")),
                        jsonPath("$.progressMethods", is("ALL")),
                        jsonPath("$.recruitmentDeadline", is("2024-10-30")),
                        jsonPath("$.contract", is("test@tesc.om")),
                        jsonPath("$.subject", is("끝내주는 서비스를 개발 해 봅시다.")),
                        jsonPath("$.content", is("사실은 윈도우앱")),
                        jsonPath("$.techStacks[0]", is("Java")),
                        jsonPath("$.positions[0]", is("Backend"))
                )
                .andDo(print());
    }

    @DisplayName("응모 ID 에 해당하는 응모글이 없으면 에러가 발생한다.")
    @Test
    void getRecruitmentByIdFail() throws Exception {
        // given
        var recruitmentId = 1L;

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/recruitments/{recruitmentId}", recruitmentId))
                .andExpect(status().isNotFound());
    }

    @DisplayName("작성한 응모글을 삭제하면 응모글 목록에서 제거된다.")
    @Test
    void deleteRecruitmentById() throws Exception {
        // given
        var recruitment = createRecruitment();

        var savedRecruitment = recruitmentsRepository.save(recruitment);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/recruitments/{recruitmentId}", savedRecruitment.getId()))
                .andExpect(status().isNoContent());
    }

    @Transactional
    @DisplayName("작성한 응모글을 수정한다")
    @Test
    void updateRecruitment() throws Exception {

        // given
        var recruitment = createRecruitment();
        var savedRecruitment = recruitmentsRepository.save(recruitment);

        var updateRecruitmentRequest = createUpdateRecruitmentRequest();
        var updateRecruitment = UpdateRecruitment.from(updateRecruitmentRequest);

        savedRecruitment.update(updateRecruitment);

        // when
        var jsonString = objectMapper.writeValueAsString(updateRecruitmentRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/recruitments/{recruitmentId}", savedRecruitment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Transactional
    @DisplayName("작성한 응모글의 수정 실패")
    @Test
    void updateFail() throws Exception {

        // given
        var updateRecruitmentRequest = createUpdateRecruitmentRequest();

        // expected
        var jsonString = objectMapper.writeValueAsString(updateRecruitmentRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/recruitments/{recruitmentId}", updateRecruitmentRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Transactional
    @DisplayName("메인 페이지에 필요한 응모글 목록을 불러올 수 있다.")
    @Test
    void getRecruitments() throws Exception {
        // given
        // TODO : Require cleansing
        var recruitment1 = createRecruitment();

        var techStack2 = TechStack.from("Kotlin");
        var position2 = Position.from("BackendEngineer");
        var recruitment2 = Recruitment.builder()
                .recruitmentCategories(RecruitmentCategories.STUDY)
                .progressMethods(ProgressMethods.ALL)
                .numberOfPeople(3)
                .progressPeriod(3)
                .recruitmentDeadline(LocalDate.of(2024, 8, 13))
                .contract("kotlin@kotlin.com")
                .subject("끝내주는 Kotlin 서비스를 개발 해 봅시다.")
                .content("사실은 Android 앱")
                .recruitmentTechStacks(new ArrayList<>())
                .build();
        var recruitmentPosition2 = RecruitmentPosition.of(position2, recruitment2);
        var recruitmentTechStack2 = RecruitmentTechStack.of(techStack2, recruitment2);
        recruitmentPosition2.makeRelationship(position2, recruitment2);
        recruitmentTechStack2.makeRelationship(techStack2, recruitment2);

        var savedRecruitments = recruitmentsRepository.saveAll(List.of(recruitment1, recruitment2));

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/recruitments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(savedRecruitments.get(0).getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recruitmentCategory", is(RecruitmentCategories.STUDY.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].techStacks[0]", is("Java")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].positions[0]", is("Backend")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recruitmentDeadline", is("2024-10-30")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].subject", is("끝내주는 서비스를 개발 해 봅시다.")))
                .andDo(print());

    }

    /**
     *  테스트에 필요한 Recruitment 객체 생성
     * @return Recruitment
     */
    private static Recruitment createRecruitment() {
        var techStack = TechStack.from("Java");
        var position = Position.from("Backend");
        var recruitment = Recruitment.builder()
                .recruitmentCategories(RecruitmentCategories.STUDY)
                .progressMethods(ProgressMethods.ALL)
                .numberOfPeople(3)
                .progressPeriod(3)
                .recruitmentDeadline(LocalDate.of(2024, 10, 30))
                .contract("test@tesc.om")
                .subject("끝내주는 서비스를 개발 해 봅시다.")
                .content("사실은 윈도우앱")
                .recruitmentTechStacks(new ArrayList<>())
                .build();
        var recruitmentPosition = RecruitmentPosition.of(position, recruitment);
        var recruitmentTechStack = RecruitmentTechStack.of(techStack, recruitment);
        recruitmentPosition.makeRelationship(position, recruitment);
        recruitmentTechStack.makeRelationship(techStack, recruitment);
        return recruitment;
    }

    /**
     * 갱신시에 사용되는 UpdateRecruitmentRequest 객체 생성
     * @return UpdateRecruitmentRequest
     */
    private static UpdateRecruitmentRequest createUpdateRecruitmentRequest() {
        return UpdateRecruitmentRequest.builder()
                .recruitmentCategories(RecruitmentCategories.PROJECT)
                .progressMethods(ProgressMethods.ONLINE)
                .techStacks(List.of("Python", "Go"))
                .recruitmentPositions(List.of("Backend22", "DevOps22", "Infra22"))
                .numberOfPeople(93)
                .progressPeriod(90)
                .recruitmentDeadline(LocalDate.of(2024, 12, 25))
                .contract("updatedEmail@test.com")
                .subject("수정된 제목")
                .content("수정된 본문")
                .build();
    }

}