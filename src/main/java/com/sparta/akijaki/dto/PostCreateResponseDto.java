package com.sparta.akijaki.dto;

import com.sparta.akijaki.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostCreateResponseDto {
    private Long num;
    private String username;
    private String content;
    private String title;
    private int price;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private String imageUrl;


    public PostCreateResponseDto(Post post) {
        this.num = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.imageUrl = post.getImageUrl();
    }
}