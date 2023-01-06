package com.project.evertimeclonecodingbackend.global.security;

import com.project.evertimeclonecodingbackend.domain.member.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("spring.jwt.secret")
    private String secretKey;

    private final Long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 30; //30분

    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String userId, String role) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // JWT 타입 지정 bearer
                .setClaims(claims) // 내용
                .setIssuedAt(now) // 발급시간
                .setIssuer("cloudwi")
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // 만료시간
                .setSubject(userId)
                .signWith(SignatureAlgorithm.HS256, secretKey) // 알고리즘, 시크릿 키
                .compact();
    }

    //토큰에서 인증정보를 조회하는 메서드
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("AccessToken");
    }

    public boolean validateAccessToken(String token) {
        token = bearerRemove(token);
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String bearerRemove(String token) {
        return token.substring("Bearer ".length());
    }
}
