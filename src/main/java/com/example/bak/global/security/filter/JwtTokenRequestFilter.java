package com.example.bak.global.security.filter;

import com.example.bak.auth.domain.TokenType;
import com.example.bak.auth.infra.jwt.JwtValidator;
import com.example.bak.auth.infra.jwt.utils.JwtUtils;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.global.security.dto.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtTokenRequestFilter extends OncePerRequestFilter {

    private static final String[] WHITE_LIST = {"/api/v1/users", "/api/v1/auth/login", "/css/*"};
    private final JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = JwtUtils.resolveToken(request.getHeader(HttpHeaders.AUTHORIZATION))
                .orElse(null);

        if (filterValidate(request, token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Jws<Claims> claims = jwtValidator.validate(TokenType.ACCESS, token);
            request.setAttribute(JwtUtils.CLAIMS_KEY, AuthInfo.from(claims));
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    ErrorCode.INVALID_TOKEN.getMessage());
        }
    }

    private boolean filterValidate(HttpServletRequest request, String token) {
        return isWhiteList(request.getRequestURI()) || token == null;
    }

    private boolean isWhiteList(String requestURL) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURL);
    }
}
