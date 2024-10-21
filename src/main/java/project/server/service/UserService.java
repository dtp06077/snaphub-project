package project.server.service;


import org.springframework.http.ResponseEntity;

import project.server.dto.request.user.UpdateNameRequestDto;
import project.server.dto.request.user.UpdateProfileImageRequestDto;
import project.server.dto.response.user.GetUserInfoResponseDto;
import project.server.dto.response.user.GetUserResponseDto;
import project.server.dto.response.user.UpdateNameResponseDto;
import project.server.dto.response.user.UpdateProfileImageResponseDto;
import project.server.security.domain.CustomUser;

public interface UserService {
    //회원 정보 조회하기
    ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(CustomUser customUser);

    //닉네임 변경하기
    ResponseEntity<? super UpdateNameResponseDto> updateName(UpdateNameRequestDto request, CustomUser customUser);

    //프로필 이미지 변경하기
    ResponseEntity<? super UpdateProfileImageResponseDto> updateProfileImage(UpdateProfileImageRequestDto request, CustomUser customUser);

    //특정 회원 정보 조회하기
    ResponseEntity<? super GetUserResponseDto> getUser(int id);
}
