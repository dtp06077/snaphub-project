package project.server.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String loginId;
    private String password;
}
