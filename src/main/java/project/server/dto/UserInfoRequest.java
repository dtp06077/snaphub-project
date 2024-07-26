package project.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoRequest {
    private String name;
    private String email;
    private String loginId;
    private String password;
    private String profile;
    private String createdAt;
}
