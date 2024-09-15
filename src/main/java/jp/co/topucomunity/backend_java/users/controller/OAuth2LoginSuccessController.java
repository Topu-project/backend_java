package jp.co.topucomunity.backend_java.users.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.topucomunity.backend_java.config.OAuth2UserPrincipal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.Duration;

@Controller
public class OAuth2LoginSuccessController implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        var oAuth2UserPrincipal = (OAuth2UserPrincipal) authentication.getPrincipal();

        var responseCookie = ResponseCookie.from("SESSION", oAuth2UserPrincipal.getJws())
                // .domain("localhost") // Todo : 운영서버에 올려서 도메인이 바뀔시 설정이 필요할 수 도 있음
                .path("/")
                .maxAge(Duration.ofMinutes(30))
                .sameSite("Strict")
                .secure(false) // Todo : 운영서버에 올릴 때는 true 로 전환
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        // Todo : Topu Service 처음 로그인한 유저이면 등록 페이지로 redirect

        // Todo : Topu Service 처음 로그인한 유저가 아니면 인덱스 페이지로 redirect
        response.sendRedirect("http://localhost:3000");
    }
}
