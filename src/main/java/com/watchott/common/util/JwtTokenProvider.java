package com.watchott.common.util;

import com.watchott.movie.dto.TokenInfoDto;
import com.watchott.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * packageName    : watchott.util
 * fileName       : JwtTokenProvider
 * author         : shipowner
 * date           : 2023-09-18
 * description    : JWT 토큰 생성 및 검증을 관리
 */

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // Access Token 만료 시간
    private final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // Refresh Token 만료 시간

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * methodName : generateToken
     * author : shipowner
     * description : 생성된 토큰 DTO로 반환
     *
     */
    public TokenInfoDto generateToken(User user) {
        return TokenInfoDto.builder()
                .grantType("Bearer")
                .accessToken(this.generateAccessToken(user))
                .refreshToken(this.generateRefreshToken(user))
                .build();
    }

    /**
     * methodName : generateAccessToken
     * author : shipowner
     * description : Access Token 생성
     *
     */
    public String generateAccessToken(User user){
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims();
        claims.put("auth",authorities);
        claims.put("email",user.getEmail());
        claims.put("name",user.getName());

        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim("auth", authorities).addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * methodName : generateRefreshToken
     * author : shipowner
     * description : Refresh Token 생성
     *
     */
    public String generateRefreshToken(User user){
        String refreshToken = Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return refreshToken;
    }

    /**
     * methodName : getAuthentication
     * author : shipowner
     * description : 토큰 정보 조회
     *
     */
    public Authentication getAuthentication(String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer")) {
            accessToken =  accessToken.substring(7);
        }

        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), String.valueOf(claims.get("email")), "", String.valueOf(claims.get("name")));
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * methodName : validateToken
     * author : shipowner
     * description : 토큰 정보 검증
     *
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
