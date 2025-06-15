package com.watchott.user.controller;

import com.watchott.common.dto.ApiResponse;
import com.watchott.movie.dto.TokenInfoDto;
import com.watchott.user.dto.UserDto;
import com.watchott.common.service.JwtTokenService;
import com.watchott.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : watchott.controller
 * fileName       : UserController
 * author         : shipowner
 * date           : 2023-09-15
 * description    :
 */

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @PostMapping(value = "/signup")
    public ApiResponse signup(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ApiResponse.fail(errorMessage);
        }

        userService.save(userDto);
        return ApiResponse.success("회원가입이 완료되었습니다.");
    }

    @PostMapping(value = "/login")
    public ApiResponse<TokenInfoDto> login(HttpServletRequest request
            , HttpServletResponse response
            , @RequestBody UserDto userDto) {

        TokenInfoDto tokenInfoDto = userService.login(userDto);
        jwtTokenService.registerRefreshToken(response, request, tokenInfoDto);
        tokenInfoDto.setRefreshToken(null);
        return ApiResponse.success(tokenInfoDto);
    }


    @PostMapping(value = "/logout")
    public ApiResponse logout(HttpServletRequest request
            , HttpServletResponse response) {

        jwtTokenService.removeToken(response, request);
        return ApiResponse.success("로그아웃이 완료되었습니다.");
    }

}
