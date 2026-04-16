package com.catholic.moyeo.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tech_stack")
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_stack_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    protected TechStack() {}

    public TechStack(String name) {
        this.name = name;
    }
}