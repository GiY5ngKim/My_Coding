package com.catholic.moyeo.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static Long currentMemberId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new IllegalStateException("Unauthenticated");
        }

        Object p = auth.getPrincipal(); // 너는 Long을 넣고 있음
        if (p instanceof Long l) return l;
        if (p instanceof String s) return Long.parseLong(s);

        throw new IllegalStateException("Invalid principal type: " + p.getClass().getName());
    }
}
