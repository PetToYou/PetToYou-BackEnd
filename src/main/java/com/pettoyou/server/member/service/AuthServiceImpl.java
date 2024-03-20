package com.pettoyou.server.member.service;

import com.pettoyou.server.auth.AuthTokenGenerator;
import com.pettoyou.server.auth.OAuthInfoResponse;
import com.pettoyou.server.auth.OAuthLoginParams;
import com.pettoyou.server.auth.RequestOAuthInfoService;
import com.pettoyou.server.config.jwt.util.JwtUtil;
import com.pettoyou.server.config.jwt.util.TokenType;
import com.pettoyou.server.config.redis.util.RedisUtil;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.dto.MemberDto;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.entity.MemberRole;
import com.pettoyou.server.member.entity.Role;
import com.pettoyou.server.member.entity.enums.MemberStatus;
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

    @Override
    public MemberDto.Response.SignIn signIn(OAuthLoginParams param) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(param);
        log.info(oAuthInfoResponse.getName());
        log.info(oAuthInfoResponse.getEmail());
        log.info(oAuthInfoResponse.getNickname());
        log.info(oAuthInfoResponse.getPhone());
        Member findMember = findByEmail(oAuthInfoResponse.getEmail()).orElse(forceJoin(oAuthInfoResponse));

        String refreshToken = redisUtil.getData(RT + findMember.getEmail());
        if (refreshToken == null) {
            String newRefreshToken = jwtUtil.createToken(findMember.getEmail(), TokenType.REFRESH_TOKEN);
            redisUtil.setData(RT + findMember.getEmail(), newRefreshToken, jwtUtil.getExpiration(TokenType.REFRESH_TOKEN));
        }

        return MemberDto.Response.SignIn.builder()
                .authTokens(authTokenGenerator.generate(findMember.getEmail(), refreshToken))
                .nickname(findMember.getNickName())
                .build();
    }

    @Override
    public MemberDto.Response.Reissue reissue(String refreshToken) {
        String emailInToken = jwtUtil.getEmailInToken(jwtUtil.resolveToken(refreshToken));

        String refreshTokenInRedis = redisUtil.getData(RT + emailInToken);

        if (!Objects.equals(refreshToken, refreshTokenInRedis)) {
            throw new CustomException(CustomResponseStatus.REFRESH_TOKEN_NOT_MATCH);
        }

        return MemberDto.Response.Reissue.builder()
                .authTokens(authTokenGenerator.generate(emailInToken))
                .build();
    }

    @Override
    public void logout(String accessToken) {
        String resolveToken = jwtUtil.resolveToken(accessToken);
        String emailInToken = jwtUtil.getEmailInToken(resolveToken);

        String refreshTokenInRedis = redisUtil.getData(RT + emailInToken);
        if (refreshTokenInRedis.isEmpty()) throw new CustomException(CustomResponseStatus.REFRESH_TOKEN_NOT_FOUND);

        redisUtil.deleteDate(RT+emailInToken);
        redisUtil.setData(LOGOUT, resolveToken, jwtUtil.getExpiration(resolveToken));
    }

    private Member forceJoin(OAuthInfoResponse joinParam) {
        Member joinMember = Member.builder()
                .name(joinParam.getName())
                .nickName(joinParam.getNickname())
                .phone(joinParam.getPhone())
                .email(joinParam.getEmail())
                .provider(joinParam.getOAuthProvider())
                .memberStatus(MemberStatus.ACTIVATE)
                .build();

        Role role = roleRepository.findByRoleType(RoleType.ROLE_MEMBER)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.ROLE_NOT_FOUND));

        MemberRole memberRole = MemberRole.builder()
                .member(joinMember)
                .role(role)
                .build();

        memberRoleRepository.save(memberRole);

        return memberRepository.save(joinMember);
    }

    private Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
