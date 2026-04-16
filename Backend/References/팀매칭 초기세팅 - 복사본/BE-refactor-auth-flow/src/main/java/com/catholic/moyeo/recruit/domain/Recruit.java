package com.catholic.moyeo.recruit.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String category;
    private String tag;
    private String department;
    private String title;
    private String content;
    private String status;
    private Integer totalHeadcount;
    private LocalDateTime deadline;
}
