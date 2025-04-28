package com.workout.workoutcom.configuration.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtBlacklistFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtUtil jwtUtil;

    public JwtBlacklistFilter(RedisTemplate<String,Object> redisTemplate,JwtUtil jwtUtil) {
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // 블랙리스트 검증 제외할 경로
        List<String> excludedPaths = List.of(
                "/api/set",
                "/api/getPublicKeyModule",
                "/api/auth/registerUser",
                "/api/auth/login",
                "/api/auth/loginCkeck",
                "/api/auth/logout"
        );

        // 화이트리스트에 있는 경로는 바로 다음 필터로 넘김
        if (excludedPaths.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtil.extractTokenFromCookie(request);
         //토큰은 있지만 블랙리스트에 올라간 경우(로그아웃 된 경우)
         if(token != null && isBlacklisted(token)){
             response.setContentType("text/plain; charset=UTF-8");
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             response.getWriter().write("해당 토큰은 차단된 토큰입니다..");
             return;
         }

         filterChain.doFilter(request, response);
    }

    //블랙리스트에 올라간 토큰인지 확인
    private boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token));
    }
}
