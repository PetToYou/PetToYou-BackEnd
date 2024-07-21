package com.pettoyou.server.config.jwt.filter;

import com.pettoyou.server.config.jwt.util.JwtUtil;
import com.pettoyou.server.config.redis.util.RedisUtil;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String EXCEPTION = "exception";
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request.getHeader("Authorization"));

        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (request.getRequestURI().equals("/api/v1/auth/reissue")) {
                log.info("재발급 요청 들어왔습니다!");
                filterChain.doFilter(request, response);
                return;
            }

            String isLogout = redisUtil.getData("LOGOUT:"+token);
            // getData 해서 값이 가져와지면 AT가 블랙리스트에 등록된 상태이므로 로그아웃된 상태임.
            if (isLogout != null) {
                request.setAttribute(EXCEPTION, CustomResponseStatus.LOGOUT_MEMBER.getMessage());
                return;
            }

            /***
             * 권한 확인 로직에서 현재 Lazy 전략의 에러가 발생함. 이를 오늘 고치도록!
             */
            Authentication authentication = jwtUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            request.setAttribute(EXCEPTION, CustomResponseStatus.EXPIRED_JWT.getMessage());
        } catch (JwtException | IllegalArgumentException | SignatureException
                 | UnsupportedJwtException | MalformedJwtException e) {
            request.setAttribute(EXCEPTION, CustomResponseStatus.BAD_JWT.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    // JWT 필터를 타지 않아도 되는 URI 를 해당 메서드에 설정
    // 이 필터에 토큰 없어도 되는 애들 넣으면 될거같은데
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/api/v1/auth/kakao", "api/v1/auth/reissue"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
