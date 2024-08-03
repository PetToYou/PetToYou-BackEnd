package com.pettoyou.server.member.repository;

import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByMemberId(Long id);
    Optional<Member> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
