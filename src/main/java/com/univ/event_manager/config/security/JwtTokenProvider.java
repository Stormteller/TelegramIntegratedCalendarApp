package com.univ.event_manager.config.security;

import com.univ.event_manager.data.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private JwtSecurityConfigProperties jwtSecurityConfigProperties;

    @Autowired
    public JwtTokenProvider(JwtSecurityConfigProperties jwtSecurityConfigProperties) {
        this.jwtSecurityConfigProperties = jwtSecurityConfigProperties;
    }

    public String createToken(String username, Collection<? extends GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(username);

        claims.put("auth", roles);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validTo = now.plusSeconds(jwtSecurityConfigProperties.getExpireIn());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(validTo.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, jwtSecurityConfigProperties.getSecretKey())
                .compact();
    }


    public String getUsernameFromToken(String token) {
        String secretKey = jwtSecurityConfigProperties.getSecretKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(jwtSecurityConfigProperties.getHeaderName());

        if (bearerToken != null && bearerToken.startsWith(jwtSecurityConfigProperties.getHeaderStartsWith())) {
            return bearerToken.split("\\s+")[1];
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecurityConfigProperties.getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("Expired or invalid JWT token");
        }
    }
}
