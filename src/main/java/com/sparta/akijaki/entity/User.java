package com.sparta.akijaki.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity(name = "Users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;
}
