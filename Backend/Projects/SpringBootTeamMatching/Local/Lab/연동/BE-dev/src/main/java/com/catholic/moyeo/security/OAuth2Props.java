package com.catholic.moyeo.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml 의 app.oauth2 설정값을 읽는 클래스
 *
 * app:
 *   oauth2:
 *     redirect-url: ...
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.oauth2")
public class OAuth2Props {

    // 로그인 성공 후 프론트로 redirect 할 URL
    private String redirectUrl;
}
