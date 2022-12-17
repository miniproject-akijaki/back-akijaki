package com.sparta.akijaki.entity;

import com.sparta.akijaki.dto.CommentRequestDto;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = true)
    private int likeCommentNum;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentLikes> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, Post post, User user) {
        this.content = requestDto.getContent();
        this.post = post;
        this.user = user;
    }
    public void updateComment(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public void updatePost(Post post) {
    }

    public List<CommentLikes> getLikeCommentList() {
        return commentLikes;
    }

    public void setLikeCommentNum(int sum) {
        this.likeCommentNum = sum;
    }

}

