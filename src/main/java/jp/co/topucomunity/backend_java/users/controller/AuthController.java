package jp.co.topucomunity.backend_java.users.controller;

import jp.co.topucomunity.backend_java.users.controller.in.SignUpRequest;
import jp.co.topucomunity.backend_java.users.controller.out.UserResponse;
import jp.co.topucomunity.backend_java.users.domain.UserSession;
import jp.co.topucomunity.backend_java.users.usecase.UserUsecase;
import jp.co.topucomunity.backend_java.users.usecase.in.RegisterUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserUsecase userUsecase;

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

}
