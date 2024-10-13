package project.server.service;


import org.springframework.http.ResponseEntity;

import project.server.dto.response.user.GetUserInfoResponseDto;
import project.server.security.domain.CustomUser;

public interface UserService {
    //회원 정보 조회
    ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(CustomUser customUser);


}
