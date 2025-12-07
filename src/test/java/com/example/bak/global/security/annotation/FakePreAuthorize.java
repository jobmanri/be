package com.example.bak.global.security.annotation;

import java.lang.reflect.Proxy;

public class FakePreAuthorize {

    public static PreAuthorize createFakePreAuthorize(String role) {
        return (PreAuthorize) Proxy.newProxyInstance(
                FakePreAuthorize.class.getClassLoader(),
                new Class[]{PreAuthorize.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("role")) {
                        return role;
                    }
                    return null;
                }
        );
    }
}
