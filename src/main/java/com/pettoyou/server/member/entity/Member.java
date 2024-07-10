package com.pettoyou.server.member.entity;

import com.pettoyou.server.alarm.entity.Alarm;
import com.pettoyou.server.auth.OAuthInfoResponse;
import com.pettoyou.server.member.entity.enums.MemberStatus;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.reserve.entity.Reserve;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.scrap.entity.Scrap;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String name; // 실명이 이어야함. -> 수정 가능하게

    @Size(min = 2, message = "최소 2글자 이상이어야 합니다.")
    private String nickName; // 따로 받아야됨

    private String phone; // 따로 받아야됨

    @Column(unique = true)
    private String email; // 따로 받아야됨

    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberRole> roles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Pet> pets = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Reserve> reserves = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Alarm> alarms = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Scrap> scraps = new ArrayList<>();

    public static Member from(OAuthInfoResponse joinParam) {
        return Member.builder()
                .name(joinParam.getName())
                .nickName(joinParam.getNickname() == null ? joinParam.getName() : joinParam.getNickname())
                .phone(joinParam.getPhone())
                .email(joinParam.getEmail())
                .provider(joinParam.getOAuthProvider())
                .providerId(joinParam.getId())
                .memberStatus(MemberStatus.ACTIVATE)
                .build();
    }
}

//Member
//-
//MemberId PK long IDENTITY
//Name string
//Nickname string
//Phone string
//Provider string # APPLE, KAKAO, NAVER
//MemberStatus string # ACTIVATE, DEACTIVATE, DORMANT
