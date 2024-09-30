package jp.co.topucomunity.backend_java.users.controller;

import jakarta.servlet.http.HttpServletResponse;
import jp.co.topucomunity.backend_java.users.controller.out.UserErrorResponse;
import jp.co.topucomunity.backend_java.users.exception.UnAuthenticationException;
import jp.co.topucomunity.backend_java.users.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@Slf4j
@RestControllerAdvice
public class UsersExceptionController {

    private static final String REDIRECT_URL = String.valueOf(URI.create("http://localhost:3000/login/retry"));

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserErrorResponse> usersExceptionHandler(UserException e) {
        log.error("[UserException] : {}", e.getMessage());
        return ResponseEntity
                .status(e.getStatusCode())
                .body(UserErrorResponse.from(e.getMessage()));
    }

    @ExceptionHandler(UnAuthenticationException.class)
    public void usersExceptionHandler(UnAuthenticationException e, HttpServletResponse response) {
        log.error("[UnAuthenticationException] : {}", e.getMessage());
        resetAuthentication();
        redirectPermanently(response, disableCookie());
    }

    private static void redirectPermanently(HttpServletResponse response, ResponseCookie expiredCookie) {
        response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
        response.setHeader(HttpHeaders.LOCATION, REDIRECT_URL);
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
