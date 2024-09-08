package jp.co.topucomunity.backend_java.configs;

import jakarta.servlet.http.Cookie;
import jp.co.topucomunity.backend_java.users.usecases.GoogleOAuth2UserUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final GoogleOAuth2UserUsecase googleOAuth2UserUsecase;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(configurationSource()));
        http.authorizeHttpRequests(httpRequest -> httpRequest
                .requestMatchers(HttpMethod.GET, "/recruitments").permitAll()
                .anyRequest().authenticated());
        http.oauth2Login(oauth2 -> {
            oauth2.userInfoEndpoint(userInfoEndpoint -> {
                userInfoEndpoint.userService(googleOAuth2UserUsecase);
            }).successHandler((request, response, authentication) -> {
                // TODO : 별도 컨트롤러로 분리
                var cookie = new Cookie("topu-test-cookie", "test12345");
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(false);

                // TODO : jwt 생성, 추가

                response.addCookie(cookie);
                response.sendRedirect("http://localhost:3000");
            });
        });
        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/"));
        return http.build();
    }

    protected CorsConfigurationSource configurationSource() {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
