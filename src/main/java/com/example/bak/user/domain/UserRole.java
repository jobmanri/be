package com.example.bak.user.domain;


import com.example.bak.global.exception.ErrorCode;

import static com.example.bak.global.exception.ErrorCode.*;

public enum UserRole {
    NORMAL,
    ADMIN;

    public static UserRole convert(String role) {
        if(role.equalsIgnoreCase("NORMAL")) {
            return NORMAL;
        }else if(role.equalsIgnoreCase("ADMIN")) {
            return ADMIN;
        }
        throw new IllegalArgumentException(CANNOT_CONVERT_ROLE.getMessage());
    }
}
