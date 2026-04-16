package com.catholic.moyeo.member.domain;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(
        name = "user_bookmark",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_target",
                        columnNames = {"user_id", "target_user_id"}
                )
        }
)
public class MemberBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 북마크 누른 사람 (나)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member user;

    // 북마크 대상 (상대)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private Member target;

    protected MemberBookmark() {}

    public MemberBookmark(Member user, Member target) {
        this.user = user;
        this.target = target;
    }
}