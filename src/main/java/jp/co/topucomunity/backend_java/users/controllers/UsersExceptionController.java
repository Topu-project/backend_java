package jp.co.topucomunity.backend_java.users.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jp.co.topucomunity.backend_java.users.exceptions.UsersException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@Slf4j
@RestControllerAdvice
public class UsersExceptionController {

    @ExceptionHandler(UsersException.class)
    public void usersExceptionHandler(UsersException e, HttpServletResponse response) {
        log.error("[Auth Exception] : {}", e.getMessage());
        resetAuthentication();
        redirectPermanently(response, disableCookie());
    }

    private static void redirectPermanently(HttpServletResponse response, ResponseCookie expiredCookie) {
        response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
        response.setHeader(HttpHeaders.LOCATION, String.valueOf(URI.create("http://localhost:3000/login"))); // Todo : redirect url 분리
        response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
    }

    private static ResponseCookie disableCookie() {
        var expiredCookie = ResponseCookie.from("SESSION", null)
                .domain("localhost")
                .path("/")
                .maxAge(-1)
                .sameSite("Strict")
                .secure(false)
                .build();
        return expiredCookie;
    }

    private static void resetAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
