package com.study.market.auth;

import com.study.market.auth.exception.ExpiredTokenException;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.domain.UserRole;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationTokenProvider{

    public static final String AUTHORIZATION = "Authorization";

    //3D로 설정했음
    @Value("${app.auth.jwt.access-expiration}")
    private Duration accessExpiration;

    // 15D로 설정했음
    @Value("${app.auth.jwt.refresh-expiration}")
    private Duration refreshExpiration;

    //secret key
    @Value("${app.auth.jwt.secret-key}")
    private String secretKey;

    public Duration getRefreshExpiration(){
        return refreshExpiration;
    } //refreshToken 만료시간을 redis에도 주기 위해서 -> redis에 있으면 코드에선 refreshToken의 만료 시간이 이제 필요 없지 않을까?

    @Override
    public String getAccessTokenFromHeader(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equalsIgnoreCase("access-token")){
                    return cookie.getValue();
                }
            }
        }

        String header = request.getHeader(AUTHORIZATION);

        if (header != null){
            if (!header.toLowerCase().startsWith("bearer ")){
                throw new RuntimeException();
            }
            return header.substring(7); //"bearer " 이라서 여긴 잘라서 추출
        }

        return null; //없으면 null
    }

    @Override
    public JwtAuthentication getAuthentication(String accessToken) {
        Jws<Claims> claimsJws = validateAccessToken(accessToken);

        Claims body = claimsJws.getBody();
        Long userId = Long.parseLong((String) body.get("userId"));
        UserRole userRole = UserRole.of((String) body.get("userRole"));

        return new JwtAuthentication(userId, userRole);
    } //userId와 userRole로 jwt 인증 정보를 생성

    public AuthenticationToken issue(Member member) {
        return JwtAuthenticationToken.builder()
                .accessToken(createAccessToken(""+member.getId(), member.getUserRole()))
                .refreshToken(createRefreshToken())
                .build();
    }

    public AuthenticationToken reissue(String accessToken, String refreshToken) {
        String validateRefreshToken = validateRefreshToken(refreshToken);
        accessToken = refreshAccessToken(accessToken);

        return JwtAuthenticationToken.builder()
                .accessToken(accessToken)
                .refreshToken(validateRefreshToken)
                .build();
    }

    private String refreshAccessToken(String accessToken) {
        String userId;
        UserRole role;
        try {
            Jws<Claims> claimsJws = validateAccessToken(accessToken);
            Claims body = claimsJws.getBody();
            userId = (String) body.get("userId");
            role = UserRole.of((String) body.get("userRole"));
        } catch (ExpiredJwtException e) {
            userId = (String) e.getClaims().get("userId");
            role = UserRole.of((String) e.getClaims().get("userRole"));
        }
        return createAccessToken(userId, role);
    }

    private String createAccessToken(String userId, UserRole role) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = now.plus(accessExpiration);

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", userId);
        payloads.put("userRole", role.getName());

        return Jwts.builder()
                .setSubject("UserInfo") //"sub":"userId"
                .setClaims(payloads)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    private String createRefreshToken() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = now.plus(refreshExpiration);
        return Jwts.builder()
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    private Jws<Claims> validateAccessToken(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(accessToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException e) {
            throw new RuntimeException();
        }
    }

    private String validateRefreshToken(String refreshToken) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(refreshToken);
            return refreshToken;
        } catch (ExpiredJwtException e) {
            return createRefreshToken();
        } catch (JwtException e) {
            throw new RuntimeException();
        }
    }

}
