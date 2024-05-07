package com.pettoyou.server.config.security.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND));

        // member의 roles를 영속성 컨텍스트에 담는 것 -> 하지만 너무 쓸모없는 코드이기 때문에 리팩토링 필요함
        member.getRoles().size();

        return new PrincipalDetails(member);
    }
}
