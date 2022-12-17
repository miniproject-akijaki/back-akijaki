package com.sparta.akijaki.controller;

import com.sparta.akijaki.dto.SignupRequestDto;
import com.sparta.akijaki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public void signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
    }
}
