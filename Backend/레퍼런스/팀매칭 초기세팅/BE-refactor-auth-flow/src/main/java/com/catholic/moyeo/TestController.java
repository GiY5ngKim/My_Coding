package com.catholic.moyeo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/oauth/callback")
    @ResponseBody
    public String callback() {
        return "구글 로그인 성공!";
    }
}

