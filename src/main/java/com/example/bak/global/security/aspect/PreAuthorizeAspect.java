package com.example.bak.global.security.aspect;

import com.example.bak.auth.infra.jwt.utils.JwtUtils;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.global.exception.VerificationException;
import com.example.bak.global.security.annotation.PreAuthorize;
import com.example.bak.global.security.dto.AuthInfo;
import com.example.bak.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class PreAuthorizeAspect {

    @Around("@annotation(preAuthorize)")
    public Object checkRoleForAuthorize(ProceedingJoinPoint proceedingJoinPoint,
            PreAuthorize preAuthorize) throws Throwable {
        ServletRequestAttributes attrs = getRequestAttributes();

        HttpServletRequest request = attrs.getRequest();

        AuthInfo authInfo = getAuthInfo(request);

        userRoleVerification(authInfo, preAuthorize.role());

        return proceedingJoinPoint.proceed();
    }

    private ServletRequestAttributes getRequestAttributes() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            return attributes;
        }
        throw new VerificationException(ErrorCode.NO_ATTRIBUTES);
    }

    private AuthInfo getAuthInfo(HttpServletRequest request) {
        if (request.getAttribute(JwtUtils.CLAIMS_KEY) instanceof AuthInfo authInfo) {
            return authInfo;
        }
        throw new VerificationException(ErrorCode.NO_TOKEN);
    }

    private void userRoleVerification(AuthInfo authInfo, String role) {
        if (role == null) {
            throw new VerificationException(ErrorCode.NO_AUTHORITY);
        }

        UserRole standardRole = UserRole.convert(role);

        if (!UserRole.verify(authInfo.role(), standardRole)) {
            throw new VerificationException(ErrorCode.NO_AUTHORITY);
        }
    }
}
