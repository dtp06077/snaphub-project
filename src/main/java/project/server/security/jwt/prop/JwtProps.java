package project.server.security.jwt.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("jwt")  //application.yml 의 하위 속성 경로
public class JwtProps {

    // 시크릿 키 : JWT 시크니처 암호화를 위한 정보
    private String secret;

}
