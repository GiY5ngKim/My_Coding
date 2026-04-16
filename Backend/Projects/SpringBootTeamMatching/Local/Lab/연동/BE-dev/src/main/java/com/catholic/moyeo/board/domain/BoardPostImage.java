package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "board_post_image")
public class BoardPostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_post_image_id")
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected BoardPostImage() {}

    public BoardPostImage(Long postId, String imageUrl) {
        this.postId = postId;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public Long getPostId() { return postId; }
    public String getImageUrl() { return imageUrl; }
}