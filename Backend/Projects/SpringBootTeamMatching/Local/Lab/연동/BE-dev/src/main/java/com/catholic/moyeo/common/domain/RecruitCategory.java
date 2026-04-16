package com.catholic.moyeo.common.domain;


import java.util.Arrays;

public enum RecruitCategory {

    PLAN("기획"),
    DEVELOP("개발"),
    DESIGN("디자인"),
    MARKETING("마케팅"),
    ETC("기타");

    private final String label;

    RecruitCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


    public static RecruitCategory from(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("category is required");
        }

        String trimmed = value.trim();

        return Arrays.stream(values())
                .filter(v ->
                        v.name().equalsIgnoreCase(trimmed) ||
                                v.label.equals(trimmed)
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category: " + value));
    }
}