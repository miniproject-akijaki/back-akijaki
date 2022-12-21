package com.sparta.akijaki.dto;

import com.sparta.akijaki.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostShowResponseDto {
    private Long num;
    private String username;
    private String content;
    private String title;
    private int price;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long likeCount;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostShowResponseDto(Post post) {
        this.num = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.price = post.getPrice();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public PostShowResponseDto(Post post, CommentListResponseDto commentList, Long postLikeCnt) {
        this.num = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.likeCount = postLikeCnt;
        this.commentList = commentList.getCommentList();
    }
}
