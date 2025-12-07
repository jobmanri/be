package com.example.bak.global.utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.global.exception.VerificationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssertionsErrorCode {

    public static void assertBusiness(Runnable runnable, ErrorCode errorCode) {
        assertThatThrownBy(runnable::run)
                .isInstanceOf(BusinessException.class)
                .hasMessage(errorCode.getMessage());
    }

    public static void assertVerification(Runnable runnable, ErrorCode errorCode) {
        assertThatThrownBy(runnable::run)
                .isInstanceOf(VerificationException.class)
                .hasMessage(errorCode.getMessage());
    }
}
