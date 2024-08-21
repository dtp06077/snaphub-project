package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.auth.JoinRequestDto;
import project.server.dto.response.auth.JoinResponseDto;

public interface AuthService {

    //회원 등록
    ResponseEntity<? super JoinResponseDto> join(JoinRequestDto request);

    //중복 아이디 조회
    ResponseEntity<? super JoinResponseDto> checkDuplicateId(String loginId);

    //중복 닉네임 조회
    ResponseEntity<? super JoinResponseDto> checkDuplicateName(String name);

}
