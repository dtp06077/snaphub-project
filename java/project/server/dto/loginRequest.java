package project.server.dto;

import lombok.Data;

@Data
public class loginRequest {
    private String loginId;
    private String password;
}
