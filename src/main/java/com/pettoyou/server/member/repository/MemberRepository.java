package com.pettoyou.server.member.repository;

import com.pettoyou.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MemberRepository extends JpaRepository<Member, Long> {
}
