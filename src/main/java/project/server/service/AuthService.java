package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.auth.JoinRequestDto;
import project.server.dto.response.auth.JoinResponseDto;

public interface AuthService {

    ResponseEntity<? super JoinResponseDto> join(JoinRequestDto request);

    ResponseEntity<? super JoinResponseDto> checkDuplicateId(String loginId);

    ResponseEntity<? super JoinResponseDto> checkDuplicateName(String name);

}
