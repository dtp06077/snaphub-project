package project.server.dto;

import lombok.Data;

@Data
public class UserJoinRequest {

    private String name;
    private String email;
    private String loginId;
    private String password;
    private String profile;

}
