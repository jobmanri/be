package com.example.bak.global.security.aspect;

import com.example.bak.global.exception.ErrorCode;
import com.example.bak.global.exception.VerificationException;
import com.example.bak.global.security.annotation.PreAuthorize;
import com.example.bak.global.security.dto.AuthInfo;
import com.example.bak.auth.infra.jwt.utils.JwtUtils;
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
    public Object checkRoleForAuthorize(ProceedingJoinPoint pjp, PreAuthorize preAuthorize) throws Throwable {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new VerificationException(ErrorCode.NO_ATTRIBUTES);
        }

        HttpServletRequest request = attrs.getRequest();

        AuthInfo auth = (AuthInfo) request.getAttribute(JwtUtils.CLAIMS_KEY);
        if(auth == null){
            throw new VerificationException(ErrorCode.NO_TOKEN);
        }

        UserRole standardRole = UserRole.convert(preAuthorize.role());

        if(!UserRole.verify(auth.role(), standardRole)){
            throw new VerificationException(ErrorCode.NO_AUTHORITY);
        }
        return pjp.proceed();
    }
}
