package com.watchott.common.service;

import com.watchott.movie.dto.TokenInfoDto;
import com.watchott.movie.entity.RefreshToken;
import com.watchott.user.entity.User;
import com.watchott.movie.repository.RefreshTokenRepository;
import com.watchott.user.repository.UserRepository;
import com.watchott.common.util.CookieUtil;
import com.watchott.common.util.JwtTokenProvider;
import com.watchott.common.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Optional;

/**
 * packageName    : watchott.core.service
 * fileName       : JwtTokenService
 * author         : shipowner
 * date           : 2023-09-26
 */


@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;


    public void registerRefreshToken(HttpServletResponse response, HttpServletRequest request, TokenInfoDto tokenInfoDto){
        CookieUtil.setCookies("refreshToken", tokenInfoDto.getRefreshToken(),  7 * 24 * 60 * 60, true, response);
        saveRefreshToken(request, tokenInfoDto);
    }

    /**
     * methodName : registerToken
     * author : shipowner
     * description : 토큰 쿠키 등록 및 DB 저장
     *
     */
    @Transactional
    public void saveRefreshToken(HttpServletRequest request, TokenInfoDto tokenInfoDto){
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenInfoDto.getAccessToken());
        User user = (User) authentication.getPrincipal();

        refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getUserId())
                .ip(RequestUtil.getClientIp(request))
                .refreshToken(tokenInfoDto.getRefreshToken())
                .build());
    }

    /**
     * methodName : removeToken
     * author : shipowner
     * description : 토큰 쿠키 및 DB에서 삭제
     *
     */
    @Transactional
    public void removeToken(HttpServletResponse response, HttpServletRequest request){
        String refreshToken = CookieUtil.getCookieVal("refreshToken", request);

        CookieUtil.removeCookie("refreshToken",response);

        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    /**
     * methodName : reissueToken
     * author : shipowner
     * description : 검증 만료된 Access Token을 Refresh Token으로 재발급
     *
     */
    @Transactional
    public String reissueToken(HttpServletRequest request) {
        String accessToken = null;

        String refreshTokenStr = CookieUtil.getCookieVal("refreshToken",request);

        //2. validateToken 메서드로 토큰 유효성 검사
        if (refreshTokenStr != null && jwtTokenProvider.validateToken(refreshTokenStr)) {
            //3. 저장된 refresh token 찾기
            Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByRefreshToken(refreshTokenStr);

            if(refreshTokenOpt.isPresent()) {
                RefreshToken refreshToken = refreshTokenOpt.get();

                if (refreshToken != null) {
                    //4. 최초 로그인한 ip 와 같은지 확인 (처리 방식에 따라 재발급을 하지 않거나 메일 등의 알림을 주는 방법이 있음)
                    if (RequestUtil.getClientIp(request).equals(refreshToken.getIp())) {

                        // 5. Redis 에 저장된 RefreshToken 정보를 기반으로 JWT Token 생성
                        Optional<User> userOptional = userRepository.findByUserId(refreshToken.getUserId());

                        if (userOptional.isPresent()) {
                            User user = userOptional.get();
                            accessToken = jwtTokenProvider.generateAccessToken(user);
                        }
                    }
                }
            }
        }

        return accessToken;
    }
}
