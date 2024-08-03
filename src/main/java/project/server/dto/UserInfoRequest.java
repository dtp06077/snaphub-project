package project.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoRequest {
    private Long userId;
    private String name;
    private String email;
    private String loginId;
    private String password;
    //프로필 이미지 데이터 타입 변경
    private String profile;
    private String createdAt;
    private List<String> auths;
    private int postCnt;
}
