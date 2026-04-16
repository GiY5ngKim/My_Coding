package com.catholic.moyeo.board.dto;

import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * 게시글 수정 요청
 *
 * 정책:
 * - null 필드는 수정하지 않는다.
 * - title / content 모두 optional이다.
 */
public class BoardUpdateRequest {

    @Size(max = 120)
    private String title;

    private String content;


    private List<String> images;

    public BoardUpdateRequest() {}

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public List<String> getImages() {
        return images;
    }
    public void setTitle(String title) { this.title = title; }

    public void setContent(String content) { this.content = content; }

    public void setImages(List<String> images) {
        this.images = images;
    }
}