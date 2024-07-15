package com.pettoyou.server.member.service.v2;

import com.pettoyou.server.auth.AuthTokenGenerator;
import com.pettoyou.server.config.jwt.util.JwtUtil;
import com.pettoyou.server.config.jwt.util.TokenType;
import com.pettoyou.server.config.redis.util.RedisUtil;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.dto.request.LoginReqDto;
import com.pettoyou.server.member.dto.response.LoginRespDto;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.entity.MemberRole;
import com.pettoyou.server.member.entity.Role;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import com.pettoyou.server.member.entity.enums.RoleType;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.member.repository.MemberRoleRepository;
import com.pettoyou.server.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthV2ServiceImpl implements AuthV2Service {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RoleRepository roleRepository;
    private final AuthTokenGenerator authTokenGenerator;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    private static final String RT = "RT:";
    private static final String LOGOUT = "LOGOUT:";

    @Override
    public LoginRespDto socialLoginWithoutApple(LoginReqDto loginReqDto, OAuthProvider provider) {
        Member loginMember = memberRepository.findByProviderAndProviderId(provider, loginReqDto.providerId())
                .orElseGet(() ->
                        forceJoin(loginReqDto, provider)
                );

        String refreshToken = redisUtil.getData(RT + loginMember.getEmail());
        if (Objects.isNull(refreshToken)) {
            refreshToken = jwtUtil.createToken(loginMember.getEmail(), TokenType.REFRESH_TOKEN);
            redisUtil.setData(RT + loginMember.getEmail(), refreshToken, jwtUtil.getExpiration(TokenType.REFRESH_TOKEN));
        }

        return LoginRespDto.from(authTokenGenerator.generate(loginMember.getEmail()));
    }

    private Member forceJoin(LoginReqDto loginReqDto, OAuthProvider provider) {
        Member joinMember = memberRepository.save(Member.from(loginReqDto, provider));

        Role role = roleRepository.findByRoleType(RoleType.ROLE_MEMBER)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.ROLE_NOT_FOUND));

        MemberRole memberRole = MemberRole.of(joinMember, role);
        memberRoleRepository.save(memberRole);

        return joinMember;
    }
}
