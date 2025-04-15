package com.workout.workoutcom.configuration.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final PrincipalDetailService principalDetailService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, PrincipalDetailService principalDetailService) {
        this.jwtUtil = jwtUtil;
        this.principalDetailService = principalDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        // 인증 필요 없는 URL은 필터 건너뜀
        if (uri.startsWith("/api/auth") || uri.equals("/api/set") || uri.equals("/api/getPublicKeyModule")||uri.equals("/api/auth/loginCkeck") ) {
            chain.doFilter(request, response);
            return;
        }
        String token =JwtUtil.extractTokenFromCookie(request);
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsername(token);
            UserDetails userDetails = principalDetailService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}