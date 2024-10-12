package jp.co.topucomunity.backend_java.users.usecase;

import jp.co.topucomunity.backend_java.config.GoogleOidcUser;
import jp.co.topucomunity.backend_java.users.domain.User;
import jp.co.topucomunity.backend_java.users.repository.UserRepository;
import jp.co.topucomunity.backend_java.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OidcAuthService extends OidcUserService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        var oidcUser = super.loadUser(userRequest);

        // extract oidc user info
        var sub = oidcUser.getSubject();
        var email = oidcUser.getEmail();
        var picture = oidcUser.getPicture();
        var fullName = oidcUser.getFullName();

        // check user
        var foundUser = userRepository.findUserByEmail(email);
        var user = foundUser.orElseGet(() -> userRepository.save(User.of(sub, email)));

        // create accessToken & refreshToken
        var accessToken = jwtUtil.generateToken(user.getUserId());
        refreshTokenService.storeRefreshToken(user.getUserId(), accessToken);

        // return oidc user
        return GoogleOidcUser.of(user, oidcUser, accessToken);
    }
}
