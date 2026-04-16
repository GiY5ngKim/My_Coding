package com.catholic.moyeo.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * 게시글 작성 요청
 *
 * body:
 * - title
 * - content
 */
public class BoardCreateRequest {

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    private String content;

    private List<String> images;




    public BoardCreateRequest() {}


    public String getTitle() { return title; }
    public String getContent() { return content; }
    public List<String> getImages() { return images; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setImages(List<String> images) { this.images = images; }

}