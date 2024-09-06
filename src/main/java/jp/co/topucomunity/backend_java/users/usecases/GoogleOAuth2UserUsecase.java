package jp.co.topucomunity.backend_java.users.usecases;

import jp.co.topucomunity.backend_java.configs.OAuth2UserPrincipal;
import jp.co.topucomunity.backend_java.users.domains.User;
import jp.co.topucomunity.backend_java.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleOAuth2UserUsecase implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);


        var email = Objects.requireNonNull(oAuth2User.getAttribute("email")).toString();
        var optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            var foundUser = optionalUser.get();
            return new OAuth2UserPrincipal(oAuth2User, foundUser.getId());
        }

        var savedUser = userRepository.save(User.from(email));
        return new OAuth2UserPrincipal(oAuth2User, savedUser.getId());
    }
}
