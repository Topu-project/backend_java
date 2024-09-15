package jp.co.topucomunity.backend_java.config.resolver;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.topucomunity.backend_java.users.domain.UserSession;
import jp.co.topucomunity.backend_java.users.exception.UnAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.instrument.IllegalClassFormatException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class JwtResolver implements HandlerMethodArgumentResolver {

    @Value("${jwt.sign.key}")
    private String jwtSignKey;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // convert to http servlet request
        var httpServletRequest =
                webRequest.getNativeRequest(HttpServletRequest.class);
        if (Objects.isNull(httpServletRequest)) {
            throw new IllegalClassFormatException();
        }

        // find session cookie
        var sessionCookie = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("SESSION"))
                .findAny()
                .orElseThrow(UnAuthenticationException::new);

        var token = sessionCookie.getValue();
        if (!StringUtils.hasText(token)) {
            throw new UnAuthenticationException();
        }

        // validateJasonWebToken
        var secretKey = Keys.hmacShaKeyFor(jwtSignKey.getBytes());

        try {
            var claimsJws =
                    Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);

            // jws 의 유효기간 검증
            if (!claimsJws.getPayload().getExpiration().after(new Date())) {
                throw new UnAuthenticationException();
            }

            return new UserSession(claimsJws.getPayload().getId());
        } catch (JwtException e) {
            throw new UnAuthenticationException(e);
        }
    }
}
