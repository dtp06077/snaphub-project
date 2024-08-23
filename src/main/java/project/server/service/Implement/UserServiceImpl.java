package project.server.service.Implement;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.server.domain.User;

import project.server.dto.response.ResponseDto;
import project.server.dto.response.user.UserInfoResponseDto;
import project.server.repository.UserRepository;
import project.server.security.domain.CustomUser;
import project.server.service.UserService;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super UserInfoResponseDto> getUserInfo(CustomUser customUser) {

        User user = customUser.getUser();

        try {
            if (user == null) {
                return UserInfoResponseDto.notExistUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return UserInfoResponseDto.success(user);
    }


}
