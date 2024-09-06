package jp.co.topucomunity.backend_java.recruitments.apidocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.topucomunity.backend_java.recruitments.controller.in.CreateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.domain.*;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import jp.co.topucomunity.backend_java.recruitments.domain.enums.RecruitmentCategories;
import jp.co.topucomunity.backend_java.recruitments.repository.RecruitmentsRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
public class RecruitmentApiDocsTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;
    private final RecruitmentsRepository recruitmentsRepository;

    @DisplayName("응모 ID 를 이용하여 해당 응모글을 응모글 목록에서 제거할 수 있다.")
    @Test
    void deleteRecruitmentById() throws Exception {
        // given
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

        var savedRecruitment = recruitmentsRepository.save(recruitment);

        // expect
        mvc.perform(RestDocumentationRequestBuilders.delete("/recruitments/{recruitmentId}", savedRecruitment.getId()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcRestDocumentation.document("delete-recruitment",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("recruitmentId").description("응모 ID")
                        )
                ))
                .andDo(print());
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
        mvc.perform(RestDocumentationRequestBuilders.post("/recruitments")
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
