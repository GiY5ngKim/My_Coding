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
    private String email;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;



    protected Member() {}

    private Member(String provider, String providerSub, String email, boolean emailVerified) {
        this.provider = provider;
        this.providerSub = providerSub;
        this.email = email;
        this.emailVerified = emailVerified;
    }

    public static Member createGoogle(String sub, String email, boolean verified) {
        return new Member("google", sub, email, verified);
    }
}
