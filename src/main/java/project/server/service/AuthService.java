package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.auth.JoinRequestDto;
import project.server.dto.response.auth.JoinResponseDto;
import project.server.dto.response.auth.LoginResponseDto;

public interface AuthService {

    //회원 등록하기
    ResponseEntity<? super JoinResponseDto> join(JoinRequestDto request);

    //중복 아이디 조회하기
    ResponseEntity<? super JoinResponseDto> checkDuplicateId(String loginId);

    //중복 닉네임 조회하기
    ResponseEntity<? super JoinResponseDto> checkDuplicateName(String name);

    //회원 로그인하기
    ResponseEntity<? super LoginResponseDto> login(String loginId, String password);

}
