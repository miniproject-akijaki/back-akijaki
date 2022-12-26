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
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    @Column
    private String imageUrl;

    @Column
    private String video;
    // 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) // , cascade = CascadeType.REMOVE
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) // , cascade = CascadeType.REMOVE
    private List<PostLikes> postLikes = new ArrayList<>();

    public Post(String title, String content, String video, String imageUrl, User user) {
        this.title = title;
        this.content = content;
        this.video = video;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public void update(String title, String content, String video, String imageUrl) {
        this.title = title;
        this.content = content;
        this.video = video;
        this.imageUrl = imageUrl;
    }
}