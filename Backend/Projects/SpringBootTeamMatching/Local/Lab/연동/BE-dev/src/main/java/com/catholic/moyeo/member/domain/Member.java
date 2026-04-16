package com.catholic.moyeo.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@Table(
        name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_app_user_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_app_user_provider_sub", columnNames = {"provider", "provider_sub"})
        }
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String provider; // google

    @Column(name = "provider_sub", nullable = false, length = 255)
    private String providerSub; // 구글 subject

    @Column(nullable = false, length = 255)
    private String email; // 로그인용 이메일

    @Column(name = "contact_email", length = 255)
    private String contactEmail; // 연락용 이메일

    @Column(name = "phone_number", length = 20)
    private String phoneNumber; // 전화번호

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(length = 20)
    private String role;

    @Column(length = 200)
    private String intro;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "profile_image_url")
    private String profileImageUrl;


    protected Member() {}

    private Member(String provider, String providerSub, String email, boolean emailVerified) {
        this.provider = provider;
        this.providerSub = providerSub;
        this.email = email;
        this.emailVerified = emailVerified;
    }

    public static Member createGoogle(String sub, String email, boolean verified) {
        Member member = new Member("google", sub, email, verified);

        String base = email.contains("@") ? email.substring(0, email.indexOf("@")) : email;
        member.nickname = base.length() > 30 ? base.substring(0, 30) : base;

        return member;
    }

    public void updateProfile(String nickname, String profileImageUrl, String role, String intro,
                              String githubUrl, String contactEmail, String phoneNumber) {
        if (nickname != null) this.nickname = nickname;
        if (profileImageUrl != null) this.profileImageUrl = profileImageUrl;
        if (role != null) this.role = role;
        if (intro != null) this.intro = intro;
        if (githubUrl != null) this.githubUrl = githubUrl;
        if (contactEmail != null) this.contactEmail = contactEmail;
        if (phoneNumber != null) this.phoneNumber = phoneNumber;
    }
}