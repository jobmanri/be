package com.example.bak.global.security.aspect.utils;

import java.lang.reflect.Proxy;
import org.aspectj.lang.ProceedingJoinPoint;

public class FakeJoinPoint {

    public static ProceedingJoinPoint createFakeJoinPoint(PreceedAction action) {
        return (ProceedingJoinPoint) Proxy.newProxyInstance(
                FakeJoinPoint.class.getClassLoader(),
                new Class[]{ProceedingJoinPoint.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("proceed")) {
                        return action.proceed(); // proceed() 호출 시 우리가 정의한 동작 실행
                    }
                    return null;
                }
        );
    }
}
