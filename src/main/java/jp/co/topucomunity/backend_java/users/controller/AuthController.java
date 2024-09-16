package jp.co.topucomunity.backend_java.users.controller;

import jp.co.topucomunity.backend_java.config.OAuth2UserPrincipal;
import jp.co.topucomunity.backend_java.users.controller.out.UserResponse;
import jp.co.topucomunity.backend_java.users.domain.UserSession;
import jp.co.topucomunity.backend_java.users.usecase.UserUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserUsecase userUsecase;

    @GetMapping("/login")
    public ResponseEntity<?> login(@Validated UserSession userSession, @AuthenticationPrincipal OAuth2UserPrincipal oAuth2User) {
        log.info("[AuthController] login success, user id = {}", userSession.id());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:3000/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId) {
        return userUsecase.getUser(userId);
    }

    // Todo : Sign up User info

}
