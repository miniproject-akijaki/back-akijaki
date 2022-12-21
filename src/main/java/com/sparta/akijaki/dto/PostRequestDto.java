package com.sparta.akijaki.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostRequestDto {
    private String username;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private int price;

    private List<MultipartFile> multipartFiles;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;
}

