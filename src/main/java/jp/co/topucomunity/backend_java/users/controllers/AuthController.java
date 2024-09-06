package jp.co.topucomunity.backend_java.users.controllers;

import jp.co.topucomunity.backend_java.configs.OAuth2UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    @GetMapping("/")
    public String login(@AuthenticationPrincipal OAuth2UserPrincipal oAuth2User) {
        log.info("user = {}", oAuth2User);
        return "hello " + oAuth2User.getName();
    }
}
