package jp.co.topucomunity.backend_java.users.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.topucomunity.backend_java.config.GoogleOidcUser;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.Duration;

@Controller
public class OidcLoginSuccessController implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oidcUser = (GoogleOidcUser) authentication.getPrincipal();

        var cookie = ResponseCookie.from("_topu_cookie", oidcUser.getAccessToken())
                .path("/")
                .sameSite(Cookie.SameSite.STRICT.name())
                .maxAge(Duration.ofMinutes(30))
                .secure(false)
                .httpOnly(true)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.sendRedirect("http://localhost:3000");
    }
}