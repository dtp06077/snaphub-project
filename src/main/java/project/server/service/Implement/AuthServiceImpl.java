package project.server.service.Implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.User;
import project.server.domain.UserAuth;
import project.server.dto.request.auth.JoinRequestDto;
import project.server.dto.response.ResponseDto;
import project.server.dto.response.auth.JoinResponseDto;
import project.server.dto.response.auth.LoginResponseDto;
import project.server.repository.UserRepository;
import project.server.service.AuthService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    //비밀번호 암호화 객체
    private final PasswordEncoder passwordEncoder;

    //TODO : AuthenticationManager DI 이슈 해결
    private final AuthenticationManager authenticationManager;

    /**
     * 회원 등록 (회원 가입)
     * 1. 비밀번호 암호화
     * 2. 회원 등록
     * 3. 권한 등록
     */
    @Transactional
    @Override
    public ResponseEntity<? super JoinResponseDto> join(JoinRequestDto request) {
        try {
            //비밀번호 암호화
            String password = request.getPassword();

            if(password.isEmpty()) {
                log.info("Password is empty");
                return JoinResponseDto.missingPassword();
            }

            String encodePw = passwordEncoder.encode(password);
            request.setPassword(encodePw);

            User user = new User(request);
            int userId = userRepository.userSave(user);

            //권한 등록
            if(userId > 0) {
                UserAuth userAuth = new UserAuth();
                userAuth.setUser(user);
                userAuth.setAuth("ROLE_USER"); //기본 권한 : 사용자 권한
                user.getAuths().add(userAuth);
                userRepository.authSave(userAuth);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return JoinResponseDto.success();
    }
    /**
     * 중복 아이디 조회
     */
    @Override
    public ResponseEntity<? super JoinResponseDto> checkDuplicateId(String loginId) {
        if(loginId.isEmpty()) {
            log.info("LoginId is empty");
            return JoinResponseDto.missingId();
        }

        User user = userRepository.findByLoginId(loginId);

        if(user != null) {
            log.info("'" + loginId + "' already exists.");
            return JoinResponseDto.duplicateId();
        }

        log.info("'" + loginId + "' does not exist.");

        return JoinResponseDto.success();
    }
    /**
     * 중복 닉네임 조회
     */
    @Override
    public ResponseEntity<? super JoinResponseDto> checkDuplicateName(String name) {
        if(name.isEmpty()) {
            log.info("name is empty");
            return JoinResponseDto.missingName();
        }

        User user = userRepository.findByName(name);

        if(user != null) {
            log.info("'" + name + "' already exists.");
            return JoinResponseDto.duplicateName();
        }

        log.info("'" + name + "' does not exist.");

        return JoinResponseDto.success();
    }

    /**
     * 회원 로그인
     * JwtAuthenticationFilter에서 모든 로그인 인증이 완료 되므로 명시적으로 생성
     */
    @Override
    public ResponseEntity<? super LoginResponseDto> login(String loginId, String password) {

        return null;
    }

}