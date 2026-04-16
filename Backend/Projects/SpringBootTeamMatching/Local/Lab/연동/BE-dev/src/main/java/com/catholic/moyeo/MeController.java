package com.catholic.moyeo;



import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//JWT 잘 들어오는지 확인하는 디버깅용 API
//memberId 찍어보는 용도
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
