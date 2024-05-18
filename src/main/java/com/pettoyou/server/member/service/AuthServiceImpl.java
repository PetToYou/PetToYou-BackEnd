package com.pettoyou.server.member.service;

import com.pettoyou.server.auth.AuthTokenGenerator;
import com.pettoyou.server.auth.OAuthInfoResponse;
import com.pettoyou.server.auth.OAuthLoginParams;
import com.pettoyou.server.auth.RequestOAuthInfoService;
import com.pettoyou.server.config.jwt.util.JwtUtil;
import com.pettoyou.server.config.jwt.util.TokenType;
import com.pettoyou.server.config.redis.util.RedisUtil;
import com.pettoyou.server.constant.entity.AuthTokens;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.dto.MemberDto;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.entity.MemberRole;
import com.pettoyou.server.member.entity.Role;
import com.pettoyou.server.member.entity.enums.RoleType;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.member.repository.MemberRoleRepository;
import com.pettoyou.server.member.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RoleRepository roleRepository;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final AuthTokenGenerator authTokenGenerator;

    private static final String RT = "RT:";
    private static final String LOGOUT = "LOGOUT:";

    @Transactional
    @Override
    public MemberDto.Response.SignIn signIn(OAuthLoginParams param) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(param);
        Member findMember = findByEmail(oAuthInfoResponse.getEmail()).orElseGet(() -> forceJoin(oAuthInfoResponse));
        String refreshToken = redisUtil.getData(RT + findMember.getEmail());
        if (refreshToken == null) {
            refreshToken = jwtUtil.createToken(findMember.getEmail(), TokenType.REFRESH_TOKEN);
            redisUtil.setData(RT + findMember.getEmail(), refreshToken, jwtUtil.getExpiration(TokenType.REFRESH_TOKEN));
        }
        return MemberDto.Response.SignIn.of(authTokenGenerator.generate(findMember.getEmail(), refreshToken), findMember.getNickName());
    }

    @Override
    public MemberDto.Response.Reissue reissue(String refreshToken) {
        String resolveToken = jwtUtil.resolveToken(refreshToken);
        String emailInToken = jwtUtil.getEmailInToken(resolveToken);

        String refreshTokenInRedis = redisUtil.getData(RT + emailInToken);

        if (!Objects.equals(resolveToken, refreshTokenInRedis)) {
            throw new CustomException(CustomResponseStatus.REFRESH_TOKEN_NOT_MATCH);
        }

        AuthTokens generate = authTokenGenerator.generate(emailInToken);
        redisUtil.setData(RT + emailInToken, generate.getRefreshToken(), jwtUtil.getExpiration(TokenType.REFRESH_TOKEN));

        return MemberDto.Response.Reissue.from(generate);
    }

    @Override
    public void logout(String accessToken) {
        String resolveToken = jwtUtil.resolveToken(accessToken);
        String emailInToken = jwtUtil.getEmailInToken(resolveToken);
        String refreshTokenInRedis = redisUtil.getData(RT + emailInToken);
        if (refreshTokenInRedis == null) throw new CustomException(CustomResponseStatus.REFRESH_TOKEN_NOT_FOUND);

        redisUtil.deleteDate(RT + emailInToken);
        redisUtil.setData(LOGOUT, resolveToken, jwtUtil.getExpiration(resolveToken));
    }

    private Member forceJoin(OAuthInfoResponse joinParam) {
        Member joinMember = Member.from(joinParam);

        Role role = roleRepository.findByRoleType(RoleType.ROLE_MEMBER)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.ROLE_NOT_FOUND));

        MemberRole memberRole = MemberRole.of(joinMember, role);

        memberRoleRepository.save(memberRole);

        return memberRepository.save(joinMember);
    }

    private Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
