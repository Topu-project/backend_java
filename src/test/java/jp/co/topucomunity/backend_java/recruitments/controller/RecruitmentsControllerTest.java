package jp.co.topucomunity.backend_java.recruitments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.topucomunity.backend_java.recruitments.controller.in.CreateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.domain.*;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import jp.co.topucomunity.backend_java.recruitments.repository.RecruitmentsRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
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
                .andDo(MockMvcResultHandlers.print());

    }

    @Transactional
    @DisplayName("응모ID로 해당 응모 상세페이지를 조회 할 수 있다.")
    @Test
    void getRecruitmentById() throws Exception {
        // given
        var techStack = TechStack.of("Java");
        var position = Position.of("Backend");
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

        var savedRecruitment = recruitmentsRepository.save(recruitment);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/recruitments/{recruitmentId}", savedRecruitment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id", is(1)),
                        jsonPath("$.recruitmentCategories", is("STUDY")),
                        jsonPath("$.progressMethods", is("ALL")),
                        jsonPath("$.recruitmentDeadline", is("2024-10-30")),
                        jsonPath("$.contract", is("test@tesc.om")),
                        jsonPath("$.subject", is("끝내주는 서비스를 개발 해 봅시다.")),
                        jsonPath("$.content", is("사실은 윈도우앱")),
                        jsonPath("$.techStacks[0]", is("Java")),
                        jsonPath("$.positions[0]", is("Backend"))
                )
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("응모 ID 에 해당하는 응모글이 없으면 에러가 발생한다.")
    @Test
    void getRecruitmentByIdFail() throws Exception {
        // given
        var recruitmentId = 1L;

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/recruitments/{recruitmentId}", recruitmentId))
                .andExpect(status().isNotFound());
    }

    @DisplayName("작성한 응모글을 삭제하면 응모글 목록에서 제거된다.")
    @Test
    void deleteRecruitmentById() throws Exception {
        // given
        var techStack = TechStack.of("Java");
        var position = Position.of("Backend");
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

        var savedRecruitment = recruitmentsRepository.save(recruitment);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/recruitments/{recruitmentId}", savedRecruitment.getId()))
                .andExpect(status().isOk());
    }
}