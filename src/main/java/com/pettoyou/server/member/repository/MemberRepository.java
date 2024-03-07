package com.pettoyou.server.member.repository;

import com.pettoyou.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Long, Member> {
}
