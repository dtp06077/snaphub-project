package project.server.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateRequest {
    private int id;
    private String name;
    private String email;
    private String loginId;
    private String password;
    //프로필 이미지 데이터 타입 변경
    private MultipartFile profile;
}
