package com.sparta.akijaki.controller;

import com.sparta.akijaki.dto.CompleteResponseDto;
import com.sparta.akijaki.dto.LoginRequestDto;
import com.sparta.akijaki.dto.SignupRequestDto;
import com.sparta.akijaki.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@Api(tags = {"User API"})
@RestController
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    public CompleteResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }


    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public CompleteResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
