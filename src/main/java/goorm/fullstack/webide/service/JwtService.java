package goorm.fullstack.webide.service;

import goorm.fullstack.webide.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public String getUsername(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return "";
        }
    }
}
