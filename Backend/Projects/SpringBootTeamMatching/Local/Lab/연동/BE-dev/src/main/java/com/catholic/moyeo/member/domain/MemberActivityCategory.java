package com.catholic.moyeo.member.domain;


import com.catholic.moyeo.common.domain.ActivityCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_activity_category")
public class MemberActivityCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_category", nullable = false, length = 50)
    private ActivityCategory activityCategory;

    public MemberActivityCategory(Member member, ActivityCategory activityCategory) {
        this.member = member;
        this.activityCategory = activityCategory;
    }
}