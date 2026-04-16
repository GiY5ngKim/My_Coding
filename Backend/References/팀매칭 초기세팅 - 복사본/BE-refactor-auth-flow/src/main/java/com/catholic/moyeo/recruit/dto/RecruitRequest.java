package com.catholic.moyeo.recruit.dto;

import java.util.List;
import java.time.LocalDateTime;

public record RecruitRequest(
    String type,
    String category,
    String tag,
    String department,
    String title,
    String content,
    List<String> skills,
    Integer totalHeadcount,
    LocalDateTime deadline
) {}
