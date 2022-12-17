package com.sparta.akijaki.entity;

import com.sparta.akijaki.dto.PostRequestDto;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String title;

    // 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int price;

    @Column
    private int likePostNum;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) // , cascade = CascadeType.REMOVE
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) // , cascade = CascadeType.REMOVE
    private List<PostLikes> postLikes = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user) {  // RequestDto에 의존하기보다 필드로 받아주는 것이 좋다.
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public void setCommentList(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comment.updatePost(this);
        this.comments.add(comment);
    }

    public void setLikePostNum(int sum) {
        this.likePostNum = sum;
    }

    public int getLikePostNum() {
        return likePostNum;
    }

    public void setPostLike(PostLikes postLikes) {
    }

    public void addPost(Post post) {

    }
}
