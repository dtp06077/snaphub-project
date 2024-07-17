package project.server.service;

import jakarta.servlet.http.HttpServletRequest;
import project.server.domain.User;
import project.server.dto.LoginRequest;
import project.server.dto.UserRequest;

public interface UserService {
    //회원 등록
    public Long insert(UserRequest userRequest) throws Exception;

    //회원 조회
    public User select(Long userId) throws Exception;

    //로그인
    public void login(LoginRequest loginRequest, HttpServletRequest request) throws Exception;

    //회원 수정
    public Long update(Long userId, UserRequest userRequest) throws Exception;

    //회원 삭제
    public void delete(Long userId) throws Exception;
}
