package com.example.bak.global.security.resolver;

import com.example.bak.global.security.annotation.AuthUser;
import com.example.bak.global.security.dto.AuthInfo;
import com.example.bak.auth.infra.jwt.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if(httpServletRequest == null) {
            return null;
        }

        AuthInfo authInfo = (AuthInfo) httpServletRequest.getAttribute(JwtUtils.CLAIMS_KEY);
        if(authInfo == null) {
            return null;
        }

        return authInfo.id();
    }
}
