package com.sparta.akijaki.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(nullable = false)
    private String title;

    @Column
    private String image;
    // 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) // , cascade = CascadeType.REMOVE
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) // , cascade = CascadeType.REMOVE
    private List<PostLikes> postLikes = new ArrayList<>();

    public Post(String title,String content, int price, User user) {  // RequestDto에 의존하기보다 필드로 받아주는 것이 좋다.
        this.title = title;
        this.content = content;
        this.price = price;
        this.user = user;
    }

    public void update(String title,String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
