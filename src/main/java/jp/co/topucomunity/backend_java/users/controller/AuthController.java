package jp.co.topucomunity.backend_java.users.controller;

import jp.co.topucomunity.backend_java.users.controller.in.SignUpRequest;
import jp.co.topucomunity.backend_java.users.controller.out.UserResponse;
import jp.co.topucomunity.backend_java.users.domain.UserSession;
import jp.co.topucomunity.backend_java.users.exception.UnAuthenticationException;
import jp.co.topucomunity.backend_java.users.repository.UserRepository;
import jp.co.topucomunity.backend_java.users.usecase.UserUsecase;
import jp.co.topucomunity.backend_java.users.usecase.in.RegisterUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserUsecase userUsecase;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserRepository userRepository;


    @GetMapping("/login")
    public ResponseEntity<?> login(@Validated UserSession userSession) {
        log.info("[AuthController] login success, user id = {}", userSession.id());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:3000/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId) {
        return userUsecase.getUser(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signup(@Validated UserSession userSession, @RequestBody SignUpRequest request) {
        userUsecase.signUp(RegisterUser.of(Long.valueOf(userSession.id()), request));
    }

    @PostMapping("exchange-token")
    public ResponseEntity<Map<String, Object>> exchangeCode(@RequestBody Map<String, String> request) {
        String idTokenValue = request.get("idToken");
        if (idTokenValue == null) {
            throw new UnAuthenticationException();
        }

        // 구글 클라이언트 등록 정보 가져오기
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("google");

        // TODO JWT Token 검증용 클래스 생성 - ID 토큰 검증
        JwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(clientRegistration.getProviderDetails().getIssuerUri());
        Jwt jwt = null;
        try {
            jwt = jwtDecoder.decode(idTokenValue);
        } catch (JwtException e) {
            System.out.println("e = " + e);
        }

        // 사용자 정보 추출
        String sub = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");
        String picture = jwt.getClaimAsString("picture");


        // TODO User DB에 등록

        // TODO 사용자 ID 반환용 DTO 작성
        return ResponseEntity.ok(Map.of("userId", "test"));

    }
}
