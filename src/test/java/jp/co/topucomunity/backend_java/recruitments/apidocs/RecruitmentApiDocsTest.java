package jp.co.topucomunity.backend_java.recruitments.apidocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.topucomunity.backend_java.recruitments.controller.in.CreateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
public class RecruitmentApiDocsTest {

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
        mockMvc.perform(RestDocumentationRequestBuilders.post("/recruitments")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-recruitment",
                        requestFields(
                                fieldWithPath("recruitmentCategories").description("The category of the recruitment."),
                                fieldWithPath("progressMethods").description("The methods of progress."),
                                fieldWithPath("techStacks").description("The list of tech stacks required."),
                                fieldWithPath("recruitmentPositions").description("The positions available for recruitment."),
                                fieldWithPath("numberOfPeople").description("The number of people to be recruited."),
                                fieldWithPath("progressPeriod").description("The period of progress in months."),
                                fieldWithPath("recruitmentDeadline").description("The deadline for recruitment."),
                                fieldWithPath("contract").description("Contact email."),
                                fieldWithPath("subject").description("The subject of the recruitment."),
                                fieldWithPath("content").description("The content of the recruitment.")
                        )));
    }
}
