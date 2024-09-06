package jp.co.topucomunity.backend_java.configs;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class OAuth2UserPrincipal implements OAuth2User {

    private final Long userId;
    private final OAuth2User oAuth2User;

    public OAuth2UserPrincipal(OAuth2User oAuth2User, Long userId) {
        this.oAuth2User = oAuth2User;
        this.userId = userId;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttributes().get("email").toString();
    }
}
