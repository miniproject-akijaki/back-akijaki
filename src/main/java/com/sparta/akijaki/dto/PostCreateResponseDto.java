package com.sparta.akijaki.dto;

import com.sparta.akijaki.entity.Post;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PostCreateResponseDto {
    private Long num;
    private String username;
    private String content;
    private String title;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public PostCreateResponseDto(Post post) {
        this.num = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}