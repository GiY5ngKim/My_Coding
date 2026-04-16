package com.catholic.moyeo.common.domain;

import java.util.Arrays;

public enum ActivityCategory {
    CONTEST("공모전"),
    ACADEMIC("수업"),

    STUDY("스터디"),
  PROJECT("프로젝트");

    private final String label;

    ActivityCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ActivityCategory from(String value) {
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