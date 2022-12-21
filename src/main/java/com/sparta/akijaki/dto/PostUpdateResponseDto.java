package com.sparta.akijaki.dto;

import com.sparta.akijaki.entity.Post;
import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class PostUpdateResponseDto {
    private Long num;
    private String username;
    private String content;
    private String title;
    private int price;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long likeCount;

    public PostUpdateResponseDto(Post post, Long postLikeCnt) {
        this.num = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.likeCount = postLikeCnt;
    }
}
