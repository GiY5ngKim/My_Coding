package com.catholic.moyeo;



import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    @GetMapping("/api/me")
    public Object me(Authentication authentication) {
        if (authentication == null) {
            return "auth=null";
        }

        return authentication.getPrincipal(); // memberId
    }
}
