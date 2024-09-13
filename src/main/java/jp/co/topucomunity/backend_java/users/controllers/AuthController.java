package jp.co.topucomunity.backend_java.users.controllers;

import jp.co.topucomunity.backend_java.configs.OAuth2UserPrincipal;
import jp.co.topucomunity.backend_java.users.domains.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
public class AuthController {

    @GetMapping("/auth/login")
    public ResponseEntity<?> login(@Validated UserSession userSession, @AuthenticationPrincipal OAuth2UserPrincipal oAuth2User) {
        log.info("[AuthController] login success, user id = {}", userSession.id());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:3000/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    // Todo : Get User info
}
