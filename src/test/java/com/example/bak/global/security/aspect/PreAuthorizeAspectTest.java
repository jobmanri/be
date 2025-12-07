package com.example.bak.global.security.aspect;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.auth.domain.TokenType;
import com.example.bak.auth.infra.jwt.JwtTokenCommandAdaptor;
import com.example.bak.auth.infra.jwt.JwtTokenProvider;
import com.example.bak.auth.infra.jwt.utils.JwtUtils;
import com.example.bak.global.security.annotation.FakePreAuthorize;
import com.example.bak.global.security.annotation.PreAuthorize;
import com.example.bak.global.security.aspect.utils.FakeJoinPoint;
import com.example.bak.global.security.dto.AuthInfo;
import com.example.bak.global.support.TestHttpRequestServlet;
import com.example.bak.global.token.TestTokenProvider;
import com.example.bak.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@DisplayName("PreAuthorize Annotation Aspect 통합 테스트")
class PreAuthorizeAspectTest {

    private final PreAuthorizeAspect preAuthorizeAspect = new PreAuthorizeAspect();

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Nested
    @DisplayName("권한이 일치할 경우, 메서드들 실행")
    class AuthorizeSuccess {

        @Test
        @DisplayName("권한이 일치할 경우")
        void success() throws Throwable {
            // * When
            JwtTokenProvider provider = TestTokenProvider.getProvider();
            JwtTokenCommandAdaptor commandAdaptor = new JwtTokenCommandAdaptor(provider);

            Long id = 1L;
            UserRole role = UserRole.ADMIN;
            String resultMessage = "Target Method Executed";
            String token = commandAdaptor.publishToken(
                    TokenType.ACCESS, id, role);

            HttpServletRequest request = TestHttpRequestServlet.createFakeRequest(token);
            request.setAttribute(JwtUtils.CLAIMS_KEY, new AuthInfo(id, role));
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            // * When
            PreAuthorize fakeAnnotation = FakePreAuthorize.createFakePreAuthorize("ADMIN");
            ProceedingJoinPoint fakeJoinPoint = FakeJoinPoint.createFakeJoinPoint(
                    () -> resultMessage);
            Object result = preAuthorizeAspect.checkRoleForAuthorize(fakeJoinPoint, fakeAnnotation);

            // * Then
            assertThat(result).isEqualTo(resultMessage);
        }
    }
}