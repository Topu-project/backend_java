package jp.co.topucomunity.backend_java.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jp.co.topucomunity.backend_java.users.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.sign.key}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(Long userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            throw new UnAuthenticationException(e);
        }
    }

    public Jwt decodeToken(String issuerUri, String idToken) {
        var jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuerUri);
        try {
            return jwtDecoder.decode(idToken);
        } catch (JwtException e) {
            throw new UnAuthenticationException(e);
        }
    }

}
