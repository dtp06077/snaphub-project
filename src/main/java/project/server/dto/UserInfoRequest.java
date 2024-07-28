package project.server.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInfoRequest {
    private Long userId;
    private String name;
    private String email;
    private String loginId;
    private String password;
    private String profile;
    private String createdAt;
    private List<String> auths;
    private int postCnt;
}
