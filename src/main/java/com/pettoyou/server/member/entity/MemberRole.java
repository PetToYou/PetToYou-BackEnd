package com.pettoyou.server.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MemberRoleId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    public static MemberRole of (Member member, Role role) {
        return MemberRole.builder()
                .member(member)
                .role(role)
                .build();
    }
}
