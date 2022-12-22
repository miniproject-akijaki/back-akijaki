package com.sparta.akijaki.dto;

import com.sparta.akijaki.entity.Post;
import com.sparta.akijaki.entity.PostLikes;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class PostShowResponseDto {
    private Long num;
    private String username;
    private String content;
    private String title;
    private int price;

    private String imageUrl;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long likeCount;
    private boolean likeCheck;

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostShowResponseDto(Post post, Optional<PostLikes> postLikes, CommentListResponseDto commentList, Long postLikeCnt) {
        this.num = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.price = post.getPrice();
        this.imageUrl = post.getImageUrl();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public PostShowResponseDto(Post post, boolean likeCheck,CommentListResponseDto commentList, Long postLikeCnt) {
        this.num = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.imageUrl = getImageUrl();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.likeCheck = likeCheck;
        this.likeCount = postLikeCnt;
        this.commentList = commentList.getCommentList();
    }
}
