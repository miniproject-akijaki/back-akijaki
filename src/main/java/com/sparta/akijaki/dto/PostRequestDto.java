package com.sparta.akijaki.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
public class PostRequestDto {
    private String username;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private int price;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;
}

