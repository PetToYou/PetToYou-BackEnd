package com.pettoyou.server.config.jwt.filter;

import com.pettoyou.server.config.jwt.util.JwtUtil;
import com.pettoyou.server.config.redis.util.RedisUtil;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request.getHeader("Authorization"));

        if (!token.isEmpty()) {
            try {
                jwtUtil.parseToken(token);
                if (!request.getRequestURI().equals("/api/v1/reissue")) {
                    /***
                     * 로그아웃 기능 만들때 참고할 코드
                     */
//                    String isLogout = redisUtil.getData(token);
//                    // getData 해서 값이 가져와지면 AT가 블랙리스트에 등록된 상태이므로 로그아웃된 상태임.
//                    if (isLogout == null) {
//                        Authentication authentication = jwtUtil.getAuthentication(token);
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    }
                    Authentication authentication = jwtUtil.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                log.error("Enter [EXPIRED TOKEN]");
                request.setAttribute("exception", CustomResponseStatus.EXPIRED_JWT.getMessage());
            } catch (JwtException | UnsupportedJwtException |
                     MalformedJwtException | IllegalArgumentException e) {
                log.error("Enter [INVALID TOKEN]");
                request.setAttribute("exception", CustomResponseStatus.BAD_JWT.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    // JWT 필터를 타지 않아도 되는 URI 를 해당 메서드에 설정
    // 이 필터에 토큰 없어도 되는 애들 넣으면 될거같은데
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/api/v1/sign-in"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
