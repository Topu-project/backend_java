package jp.co.topucomunity.backend_java.configs;

import jp.co.topucomunity.backend_java.users.usecases.GoogleOAuth2UserUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final GoogleOAuth2UserUsecase googleOAuth2UserUsecase;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.oauth2Login(oauth2 -> {
                    oauth2.userInfoEndpoint(userInfoEndpoint -> {
                        userInfoEndpoint.userService(googleOAuth2UserUsecase);
                    });
                })
                .authorizeHttpRequests(httpRequest ->
                        httpRequest.anyRequest().authenticated());
        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/"));
        return http.build();
    }

}
