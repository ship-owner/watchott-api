package com.watchott.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchott.common.dto.ApiResponse;
import com.watchott.common.filter.JwtAuthenticationFilter;
import com.watchott.common.service.JwtTokenService;
import com.watchott.common.util.JwtTokenProvider;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import java.io.IOException;

/**
 * packageName    : watchott.config
 * fileName       : SecurityConfig
 * author         : shipowner
 * date           : 2023-09-15
 * description    : Spring Security / JWT 사용자 인증 및 보안 설정
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenService jwtTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement((sessionManagement) ->
                       sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name())
                                , new AntPathRequestMatcher("/")
                                , new AntPathRequestMatcher("/movie/index")
                                , new AntPathRequestMatcher("/movie/trend")
                                , new AntPathRequestMatcher("/movie/latest")
                                , new AntPathRequestMatcher("/user/login")
                                , new AntPathRequestMatcher("/user/signup")
                                , new AntPathRequestMatcher("/user/reissue")
                        ).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, jwtTokenService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.setContentType("application/json;charset=UTF-8");

                                // ApiResponse 형식으로 변경
                                String jsonResponse = new ObjectMapper().writeValueAsString(
                                        ApiResponse.fail("인증이 필요합니다. 로그인 해주세요.")
                                );
                                response.getWriter().write(jsonResponse);
                            }
                        })
                )
                .build();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

}
