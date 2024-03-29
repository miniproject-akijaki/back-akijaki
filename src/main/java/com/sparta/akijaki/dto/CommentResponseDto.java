package com.sparta.akijaki.dto;

import com.sparta.akijaki.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long num;
    private String username;
    private String nickname;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private boolean commentLikeCheck;
    private Long likeCount=0l;

    public CommentResponseDto(Comment comment) {
        this.num = comment.getId();
        this.username = comment.getUser().getUsername();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
    public CommentResponseDto(Comment comment, Long commentLikeCnt) {
        this.num = comment.getId();
        this.username = comment.getUser().getUsername();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likeCount = commentLikeCnt;
    }
    public CommentResponseDto(Comment comment, boolean commentLikeCheck,Long commentLikeCnt) {
        this.num = comment.getId();
        this.username = comment.getUser().getUsername();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.commentLikeCheck = commentLikeCheck;
        this.likeCount = commentLikeCnt;
    }
}
