package com.example.bak.global.support;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class TestHttpRequestServlet {

    public static HttpServletRequest createFakeRequest(String token) {
        final Map<String, Object> attributes = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer" + token);

        return (HttpServletRequest) Proxy.newProxyInstance(
                TestHttpRequestServlet.class.getClassLoader(),
                new Class[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    String methodName = method.getName();

                    if (methodName.equals("getHeader")) {
                        String headerName = (String) args[0];
                        return headers.get(headerName);
                    }
                    if (methodName.equals("setAttribute")) {
                        attributes.put((String) args[0], args[1]);
                        return null;
                    }
                    if (methodName.equals("getAttribute")) {
                        return attributes.get(args[0]);
                    }
                    return null;
                }
        );
    }
}
