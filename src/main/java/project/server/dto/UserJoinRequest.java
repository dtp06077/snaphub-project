package project.server.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserJoinRequest {

    private String name;
    private String email;
    private String loginId;
    private String password;
    //프로필 이미지 데이터타입 변경
    private String profileImage;

}
