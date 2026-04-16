package com.catholic.moyeo.recruit.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * 모집글 생성 요청
 *
 * API 명세 body:
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
 * - author는 서버에서 인증 사용자로 결정한다.
 * - status는 서버에서 OPEN으로 강제한다.
 * - applicantCount는 서버에서 초기값을 설정한다.
 *
 * 값 고정 필드:
 * - type, category는 DTO에서 String으로 받고
 *   서비스에서 허용값 검증 후 400으로 처리한다.
 *
 * 카테고리 2단계 정책:
 * - type     = 1차 필터(ActivityCategory)
 * - category = 2차 필터(RecruitCategory)
 *
 * 레거시/호환 정책:
 * - 현재 API 명세와 기존 프론트 호환을 위해 필드명은 type/category를 유지한다.
 * - 필요 시 activityCategory / recruitCategory alias getter/setter도 함께 제공한다.
 *
 * skills 저장 정책:
 * - API는 List<String>으로 받고
 * - 서버에서 CSV 문자열로 변환하여 저장한다.
 *
 * tag 정책:
 * - tag는 표시용 단일 문자열이다.
 * - 검색/필터 파라미터로는 사용하지 않는다.
 *
 * department 정책:
 * - department는 표시용 단일 문자열이다.
 * - 분류/검색/필터 파라미터로는 사용하지 않는다.
 * - 사용자가 원할 때만 입력한다.
 */
public class RecruitCreateRequest {

    /**
     * 1차 카테고리
     * - ActivityCategory Enum name 또는 label을 받는다.
     */
    @NotBlank
    @Size(max = 50)
    private String type;

    /**
     * 2차 카테고리
     * - RecruitCategory Enum name 또는 label을 받는다.
     */
    @NotBlank
    @Size(max = 50)
    private String category;

    @Size(max = 50)
    private String tag;

    /**
     * 표시용 학과 문자열
     * - 분류/검색/필터에는 사용하지 않는다.
     * - 사용자가 원할 때만 입력한다.
     */
    @Size(max = 50)
    private String department;

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    private String content;

    /**
     * required_skills
     * - API: List<String>
     * - DB: CSV 문자열
     */
    @Valid
    private List<@NotBlank @Size(max = 50) String> skills;

    @NotNull
    @Min(1)
    private Integer totalHeadcount;

    private LocalDate deadline;

    public RecruitCreateRequest() {}

    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getTag() { return tag; }
    public String getDepartment() { return department; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public List<String> getSkills() { return skills; }
    public Integer getTotalHeadcount() { return totalHeadcount; }
    public LocalDate getDeadline() { return deadline; }

    /**
     * alias getter
     * - activityCategory == type
     */
    public String getActivityCategory() { return type; }

    /**
     * alias getter
     * - recruitCategory == category
     */
    public String getRecruitCategory() { return category; }

    public void setType(String type) { this.type = type; }
    public void setCategory(String category) { this.category = category; }
    public void setTag(String tag) { this.tag = tag; }
    public void setDepartment(String department) { this.department = department; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    public void setTotalHeadcount(Integer totalHeadcount) { this.totalHeadcount = totalHeadcount; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    /**
     * alias setter
     * - activityCategory 입력을 type에 매핑한다.
     */
    public void setActivityCategory(String activityCategory) { this.type = activityCategory; }

    /**
     * alias setter
     * - recruitCategory 입력을 category에 매핑한다.
     */
    public void setRecruitCategory(String recruitCategory) { this.category = recruitCategory; }
}