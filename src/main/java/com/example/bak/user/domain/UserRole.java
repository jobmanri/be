package com.example.bak.user.domain;

import static com.example.bak.global.exception.ErrorCode.*;

public enum UserRole {
    NORMAL,
    ADMIN;

    public static UserRole convert(String role) {
        if(NORMAL.name().equalsIgnoreCase(role)) {
            return NORMAL;
        }else if(ADMIN.name().equalsIgnoreCase(role)) {
            return ADMIN;
        }
        throw new IllegalArgumentException(CANNOT_CONVERT_ROLE.getMessage());
    }

    public static boolean verify(UserRole role, UserRole standardRole) {
        if(role.equals(UserRole.ADMIN)){
            return true;
        }
        return role.equals(UserRole.NORMAL) && role.equals(standardRole);
    }
}
