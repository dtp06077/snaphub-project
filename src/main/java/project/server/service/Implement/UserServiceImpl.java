package project.server.service.Implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.server.domain.User;

import project.server.dto.request.user.UpdateNameRequestDto;
import project.server.dto.request.user.UpdateProfileImageRequestDto;
import project.server.dto.response.ResponseDto;
import project.server.dto.response.post.UploadPostResponseDto;
import project.server.dto.response.user.GetUserInfoResponseDto;
import project.server.dto.response.user.GetUserResponseDto;
import project.server.dto.response.user.UpdateNameResponseDto;
import project.server.dto.response.user.UpdateProfileImageResponseDto;
import project.server.repository.UserRepository;
import project.server.security.domain.CustomUser;
import project.server.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * 회원 정보 가져오기
     */
    @Override
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(CustomUser customUser) {

        User user;

        try {
            User authUser = customUser.getUser();
            user = userRepository.findById(authUser.getId());

            if(user == null) {
                return UploadPostResponseDto.noExistUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserInfoResponseDto.success(user);
    }

    /**
     * 닉네임 변경하기
     */
    @Override
    @Transactional
    public ResponseEntity<? super UpdateNameResponseDto> updateName(UpdateNameRequestDto request, CustomUser customUser) {

        try {
            User authUser = customUser.getUser();
            User user = userRepository.findById(authUser.getId());

            if(user == null) {
                return UploadPostResponseDto.noExistUser();
            }

            String name = request.getName();
            if(userRepository.findByName(name)!=null) {
                return UpdateNameResponseDto.duplicateName();
            }

            user.setName(name);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return UpdateNameResponseDto.success();
    }

    /**
     * 프로필 이미지 변경하기
     */
    @Override
    @Transactional
    public ResponseEntity<? super UpdateProfileImageResponseDto> updateProfileImage(
            UpdateProfileImageRequestDto request, CustomUser customUser) {

        try {
            User authUser = customUser.getUser();
            User user = userRepository.findById(authUser.getId());

            if(user == null) {
                return UploadPostResponseDto.noExistUser();
            }

            String profileImage = request.getProfileImage();
            user.setProfileImage(profileImage);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return UpdateProfileImageResponseDto.success();
    }

    /**
     * 특정 회원 정보 조회하기
     */
    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(int id) {

        User user;

        try {
            user = userRepository.findById(id);

            if(user == null) {
                return GetUserResponseDto.noExistUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserResponseDto.success(user);
    }
}
