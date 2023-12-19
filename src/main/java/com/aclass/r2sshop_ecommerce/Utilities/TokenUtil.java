package com.aclass.r2sshop_ecommerce.Utilities;

import com.aclass.r2sshop_ecommerce.models.dto.token.AccessTokenGenerated;
import com.aclass.r2sshop_ecommerce.models.dto.token.RefreshTokenGenerated;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenUtil {

    @Value("${jwt.duration}")
    public Integer duration;

    @Value("${jwt.secret}")
    private String secret;

    //Sinh token
    public AccessTokenGenerated generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        Date expiredIn = new Date(System.currentTimeMillis() + duration * 1000);

        return AccessTokenGenerated.builder()
                .accessToken(Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(expiredIn)
                        .signWith(SignatureAlgorithm.HS512, secret).compact())
                .expiredIn(expiredIn)
                .build();
    }

    public RefreshTokenGenerated generateRefreshToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        Date expiredIn = new Date(System.currentTimeMillis() + duration * 1000);

        return RefreshTokenGenerated.builder()
                .refreshToken(Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(expiredIn)
                        .signWith(SignatureAlgorithm.HS512, secret).compact())
                .expiredIn(expiredIn)
                .build();
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token" + ex.getMessage(), ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token" + ex.getMessage(), ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token" + ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty." + ex.getMessage(), ex);
        }
        return false;
    }
}