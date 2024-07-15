package project.server.service;

import jakarta.servlet.http.HttpServletRequest;
import project.server.domain.User;

public interface UserService {
    //회원 등록
    public Long insert(User user) throws Exception;

    //회원 조회
    public User select(Long userId) throws Exception;

    //로그인
    public void login(User user, HttpServletRequest request) throws Exception;

    //회원 수정
    public Long update(User user) throws Exception;

    //회원 삭제
    public void delete(Long userId) throws Exception;
}
