package com.catholic.moyeo.recruit.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * 모집글 수정 요청
 *
 * API 명세 body(optional):
 * - type
 * - category
 * - tag
 * - department
 * - title
 * - content
 * - skills
 * - totalHeadcount
 * - deadline
 *
 * 정책:
 * - null 필드는 수정하지 않는다.
 * - type/category 허용값 검증은 서비스에서 수행한다.
 * - totalHeadcount는 값이 들어온 경우 1 이상이어야 한다.
 * - totalHeadcount를 applicantCount보다 작게 줄이는 것은 서비스에서 400 처리한다.
 * - department는 표시용 선택 문자열이며 분류/검색/필터에 사용하지 않는다.
 */
public class RecruitUpdateRequest {

    @Size(max = 50)
    private String type;

    @Size(max = 50)
    private String category;

    @Size(max = 50)
    private String tag;

    @Size(max = 50)
    private String department;

    @Size(max = 120)
    private String title;

    private String content;

    @Valid
    private List<@Size(max = 50) String> skills;

    @Min(1)
    private Integer totalHeadcount;

    private LocalDate deadline;

    public RecruitUpdateRequest() {}

    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getTag() { return tag; }
    public String getDepartment() { return department; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public List<String> getSkills() { return skills; }
    public Integer getTotalHeadcount() { return totalHeadcount; }
    public LocalDate getDeadline() { return deadline; }

    public void setType(String type) { this.type = type; }
    public void setCategory(String category) { this.category = category; }
    public void setTag(String tag) { this.tag = tag; }
    public void setDepartment(String department) { this.department = department; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    public void setTotalHeadcount(Integer totalHeadcount) { this.totalHeadcount = totalHeadcount; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
}