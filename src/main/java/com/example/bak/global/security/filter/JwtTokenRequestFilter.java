package com.example.bak.global.security.filter;

import com.example.bak.auth.infra.jwt.JwtValidator;
import com.example.bak.auth.infra.jwt.persistence.TokenType;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.global.security.dto.AuthInfo;
import com.example.bak.auth.infra.jwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenRequestFilter extends OncePerRequestFilter {
    private static final String[] whiteList = {"/api/v1/users", "/api/v1/auth/login", "/css/*"};
    private final JwtValidator jwtValidator;

    public JwtTokenRequestFilter(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURL = request.getRequestURI();
        if(isWhiteList(requestURL)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = JwtUtils.resolveToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            Jws<Claims> claims = jwtValidator.validate(TokenType.ACCESS, token);
            request.setAttribute(JwtUtils.CLAIMS_KEY, AuthInfo.from(claims));
        }catch(JwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorCode.INVALID_TOKEN.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURL) {
        return PatternMatchUtils.simpleMatch(whiteList, requestURL);
    }
}
