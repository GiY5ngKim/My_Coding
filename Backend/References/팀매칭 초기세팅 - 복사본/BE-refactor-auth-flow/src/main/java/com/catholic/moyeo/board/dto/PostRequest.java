package com.catholic.moyeo.board.dto;

import java.util.List;

public record PostRequest(
    String title,
    String content,
    List<String> images
) {}
