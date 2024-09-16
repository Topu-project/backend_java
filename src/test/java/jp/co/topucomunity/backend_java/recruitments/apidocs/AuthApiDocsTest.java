package jp.co.topucomunity.backend_java.recruitments.apidocs;

import jp.co.topucomunity.backend_java.recruitments.domain.Position;
import jp.co.topucomunity.backend_java.recruitments.repository.PositionsRepository;
import jp.co.topucomunity.backend_java.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static jp.co.topucomunity.backend_java.users.domain.User.createFirstLoggedInUser;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class AuthApiDocsTest {

    private final UserRepository userRepository;
    private final PositionsRepository positionsRepository;
    private final MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        positionsRepository.deleteAllInBatch();
    }

    @DisplayName("유저 ID 로 유저 정보를 조회할 수 있다")
    @Test
    void getUserById() throws Exception {
        // given
        var backend = Position.from("Backend");

        var savedUser = userRepository.save(createFirstLoggedInUser(
                "test-user@test.com",
                "test-user",
                backend,
                "우아한형제들",
                true,
                5,
                "안녕하세요 우아한형제들에서 결제 서비스를 담당하고 있는 백엔드 엔지니어입니다.",
                "https://github.com/wooah/backend/god/king/good, https://github.com/wooah/backend/god/king/good"
        ));

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/auth/{userId}", savedUser.getUserId()))
                .andDo(MockMvcRestDocumentation.document("get-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("userId").description("유저 ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("userId").description("유저 ID"),
                                PayloadDocumentation.fieldWithPath("email").description("유저 Email 주소"),
                                PayloadDocumentation.fieldWithPath("nickname").description("유저 닉네임"),
                                PayloadDocumentation.fieldWithPath("position").description("유저 직책"),
                                PayloadDocumentation.fieldWithPath("affiliation").description("유저 소속"),
                                PayloadDocumentation.fieldWithPath("isPublicAffiliation").description("유저 소속 공개 플래그"),
                                PayloadDocumentation.fieldWithPath("personalHistoryYear").description("유저 경력"),
                                PayloadDocumentation.fieldWithPath("selfIntroduction").description("유저 자기소개"),
                                PayloadDocumentation.fieldWithPath("links").description("유저 링크")
                        )
                ))
                .andDo(print());
    }

    @DisplayName("지정한 유저 ID 로 유저를 찾을 수 없으면 에러메시지를 반환한다.")
    @Test
    void getUserById_Fail() throws Exception {
        // given
        var userId = 99L;

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/auth/{userId}", userId))
                .andDo(MockMvcRestDocumentation.document("get-user-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("userId").description("유저 ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("errorMessage").description("에러 메시지"),
                                PayloadDocumentation.fieldWithPath("validationErrors").description("검증 에러")
                        )
                ))
                .andDo(print());
    }
}
