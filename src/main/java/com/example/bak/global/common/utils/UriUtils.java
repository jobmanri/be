package com.example.bak.global.common.utils;

import java.net.URI;
import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@UtilityClass
public class UriUtils {

    public static URI current(Object... segments) {
        var builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        for (Object segment : segments) {
            builder.path("/" + segment);
        }
        return builder.build().toUri();
    }
}
