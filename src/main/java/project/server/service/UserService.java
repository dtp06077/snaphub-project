package project.server.service;

import jakarta.servlet.http.HttpServletRequest;
import project.server.domain.User;
import project.server.dto.UserInfoRequest;
import project.server.dto.UserLoginRequest;
import project.server.dto.UserJoinRequest;
import project.server.dto.UserUpdateRequest;

public interface UserService {
    //회원 등록
    public int insert(UserJoinRequest userRequest) throws Exception;

    //회원 조회
    public UserInfoRequest select(User user);

    //로그인 아이디로 회원 조회
    public User selectByLoginId(String loginId);

    //회원 이름으로 회원 조회
    public User selectByName(String name);

    //로그인
    public void login(UserLoginRequest loginRequest, HttpServletRequest request) throws Exception;

    //회원 수정
    public int update(UserUpdateRequest request) throws Exception;

    //회원 삭제
    public int delete(String loginId) throws Exception;
}
