package com.pettoyou.server.member.entity;

import com.pettoyou.server.member.entity.enums.MemberStatus;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import com.pettoyou.server.pet.entity.Pet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 실명이 이어야함. -> 수정 가능하게

    @Size(min=2, message = "최소 2글자 이상이어야 합니다.")
    private String nickName; // 따로 받아야됨

    private String phone; // 따로 받아야됨

    private String email; // 따로 받아야됨

    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;
    //가입 정보 - apple, kakao, naver

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();


}

//Member
//-
//MemberId PK long IDENTITY
//Name string
//Nickname string
//Phone string
//Provider string # APPLE, KAKAO, NAVER
//MemberStatus string # ACTIVATE, DEACTIVATE, DORMANT
