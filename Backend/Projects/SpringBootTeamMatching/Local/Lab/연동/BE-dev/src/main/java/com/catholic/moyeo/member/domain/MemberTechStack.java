package com.catholic.moyeo.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

//중간테이블
@Entity
@Getter
@Table(name = "user_tech_stack")
public class MemberTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_stack_id")
    private TechStack techStack;

    protected MemberTechStack() {}

    public MemberTechStack(Member member, TechStack techStack) {
        this.member = member;
        this.techStack = techStack;
    }
}