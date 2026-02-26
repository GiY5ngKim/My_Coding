package com.cat.picshare.user.entity; // 사용자 엔티티 패키지

import jakarta.persistence.Column; // 컬럼 매핑
import jakarta.persistence.Entity; // 엔티티 선언
import jakarta.persistence.GeneratedValue; // PK 자동 생성
import jakarta.persistence.GenerationType; // 자동 생성 전략
import jakarta.persistence.Id; // PK 표시
import jakarta.persistence.Table; // 테이블 매핑

@Entity // JPA 엔티티 선언
@Table(name = "users") // 테이블명 지정
public class User { // 사용자 엔티티

    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment 전략(H2에서도 무난)
    private Long id; // 사용자 ID

    @Column(nullable = false, unique = true, length = 100) // 이메일은 필수/유니크
    private String email; // 로그인 식별자(이메일)

    @Column(nullable = false, length = 100) // 비밀번호 해시 저장
    private String password; // 비밀번호(해시)

    protected User() { } // JPA 기본 생성자(필수)

    public User(String email, String password) { // 생성자
        this.email = email; // 이메일 저장
        this.password = password; // 비밀번호 해시 저장
    }

    public Long getId() { return id; } // id getter
    public String getEmail() { return email; } // email getter
    public String getPassword() { return password; } // password getter
} // 클래스 종료