package jp.co.topucomunity.backend_java.users.usecases;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jp.co.topucomunity.backend_java.configs.OAuth2UserPrincipal;
import jp.co.topucomunity.backend_java.users.domains.User;
import jp.co.topucomunity.backend_java.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleOAuth2UserUsecase implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Value("${jwt.sign.key}")
    private String jwtSignKey;

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        var email = Objects.requireNonNull(oAuth2User.getAttribute("email")).toString();
        var optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isPresent()) {
            var foundUser = optionalUser.get();
            var jws = generateJws(foundUser);
            return new OAuth2UserPrincipal(foundUser.getUserId(), jws, oAuth2User);
        }

        var savedUser = userRepository.save(User.of(oAuth2User.getAttribute("sub"), email));

        // Todo : Refresh Token

        var jws = generateJws(savedUser);
        return new OAuth2UserPrincipal(savedUser.getUserId(), jws, oAuth2User);
    }

    private String generateJws(User savedUser) {
        var secretKey = Keys.hmacShaKeyFor(jwtSignKey.getBytes());

        var jws = Jwts.builder()
                .id(String.valueOf(savedUser.getUserId()))
                .subject(savedUser.getSub())
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(30).toInstant()))
                .issuedAt(new Date())
                .signWith(secretKey)
                .compact();
        return jws;
    }
}
