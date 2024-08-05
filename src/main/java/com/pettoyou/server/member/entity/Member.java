package com.pettoyou.server.member.entity;

import com.pettoyou.server.alarm.entity.Alarm;
import com.pettoyou.server.auth.OAuthInfoResponse;
import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
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
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private String name; // 실명이 이어야함. -> 수정 가능하게

    @Size(min = 2, message = "최소 2글자 이상이어야 합니다.")
    private String nickName; // 따로 받아야됨

    @Column(nullable = false)
    private String phone; // 따로 받아야됨, 전화번호를 등록안한 유저의 경우엔 날라오지 않음.

    @Column(unique = true, nullable = false)
    private String email; // 따로 받아야됨

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OAuthProvider provider;

    @Column(nullable = false)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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

    public void modifyInfo(String newNickname) {
        this.nickName = newNickname;
    }

    @Builder
    public Member(MemberStatus memberStatus, String providerId, OAuthProvider provider, String email, String phone, String nickName, String name, Long memberId) {
        this.memberStatus = memberStatus;
        this.providerId = providerId;
        this.provider = provider;
        this.email = email;
        this.phone = phone;
        this.nickName = nickName;
        this.name = name;
        this.memberId = memberId;
    }

    public void validateMemberAuthorization(Long authMemberId) {
        if (!this.memberId.equals(authMemberId)) {
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_MATCH);
        }
    }
}