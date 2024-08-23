package jp.co.topucomunity.backend_java.recruitments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.topucomunity.backend_java.recruitments.controller.in.CreateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
@SpringBootTest
@RequiredArgsConstructor
class RecruitmentsControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}