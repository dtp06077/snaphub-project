package project.server.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.UserAuth;
import project.server.domain.User;
import project.server.dto.UserInfoRequest;
import project.server.dto.UserLoginRequest;
import project.server.dto.UserJoinRequest;
import project.server.dto.UserUpdateRequest;
import project.server.repository.UserRepository;

import java.io.File;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    // 비밀번호 암호와 객체
    private final PasswordEncoder passwordEncoder;
    // 인증 관리자 객체
    private final AuthenticationManager authenticationManager;

    /**
     * 회원 등록 (회원 가입)
     * 1. 비밀번호 암호화
     * 2. 회원 등록
     * 3. 권한 등록
     */
    @Transactional
    @Override
    public int insert(UserJoinRequest request) throws Exception {
        //비밀번호 암호화
        String password = request.getPassword();
        String encodePw = passwordEncoder.encode(password);
        request.setPassword(encodePw);

        User user = new User();

//        if(request.getProfile()!=null && !request.getProfile().isEmpty()) {
//            // 파일 저장 경로 설정
//            String uploadDir = "C:\\Users\\dtp06\\Spring project\\snaphub-project\\profileImage"; // 파일을 저장할 디렉토리
//            String fileName = System.currentTimeMillis() + "_" + request.getProfile().getOriginalFilename(); // 파일 이름 생성
//            File directory = new File(uploadDir);
//
//            // 디렉토리가 존재하지 않으면 생성
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            // 파일 저장
//            File uploadFile = new File(directory, fileName);
//            request.getProfile().transferTo(uploadFile); // 파일 저장
//
//            // 웹에서 접근 가능한 경로 설정 (예시: http://yourserver.com/profileImage/)
//            String webPath = "http://snabhub.com/profileImage/" + fileName; // 웹 경로 설정
//            user.setProfileImage(webPath); // DB에 저장할 경로 설정
//        }
        //회원 등록
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setLoginId(request.getLoginId());
        user.setPassword(request.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        int userId = userRepository.userSave(user);

        //권한 등록
        if(userId > 0) {
            UserAuth userAuth = new UserAuth();
            userAuth.setUser(user);
            userAuth.setAuth("ROLE_USER"); //기본 권한 : 사용자 권한
            user.getAuths().add(userAuth);
            userRepository.authSave(userAuth);
        }

        return userId;
    }

    /**
     * PK를 활용한 회원 조회
     */
    @Override
    public UserInfoRequest select(User user) {
        UserInfoRequest userInfo = new UserInfoRequest();
        User findUser = userRepository.findById(user.getId());

        //CustomUser 객체의 User 값을 그대로 전달받는게 아닌
        //CustomUser 객체에서 받은 User 의 id 값을 영속성 컨텍스트에서 탐색한 값을 사용
        //CustomUser의 User != DB에 저장된 User 이기 때문.
        userInfo.setUserId(findUser.getId());
        userInfo.setLoginId(findUser.getLoginId());
        userInfo.setPassword(findUser.getPassword());
        userInfo.setProfileImage(findUser.getProfileImage());
        userInfo.setName(findUser.getName());
        userInfo.setEmail(findUser.getEmail());
        userInfo.setAuths(findUser.getRoles());
        userInfo.setPostCnt(findUser.getPostCnt());

        return userInfo;
    }

    /**
     * 로그인 아이디를 활용한 회원 조회
     *
     */
    @Override
    public User selectByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    /**
     * 닉네임을 활용한 회원 조회
     */
    @Override
    public User selectByName(String name) {
        return userRepository.findByName(name);
    }

    /**
     * 회원 로그인
     */
    @Transactional
    @Override
    public void login(UserLoginRequest loginRequest, HttpServletRequest request) {
        String loginId = loginRequest.getLoginId();
        String password = loginRequest.getPassword();
        log.info("loginId : " + loginId);
        log.info("password : " + password);

        //AuthenticationManager
        //아이디, 패스워드 인증 토큰 생성
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(loginId, password);

        // 토큰에 요청정보 등록
        token.setDetails(new WebAuthenticationDetails(request));

        // 토큰을 이용하여 인증 요청 - 로그인
        Authentication authentication = authenticationManager.authenticate(token);

        log.info("인증 여부 : "+ authentication.isAuthenticated() );

        org.springframework.security.core.userdetails.User authUser
                = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        log.info("인증된 사용자 아이디 : " + authUser.getUsername());

        // 시큐리티 컨텍스트에 인증 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 회원정보 수정
     */
    @Transactional
    @Override
    public int update(UserUpdateRequest request) throws Exception {
        // 비밀번호 암호화
        String password = request.getPassword();
        String encodedPw = passwordEncoder.encode(password);
        request.setPassword(encodedPw);

        //변경 감지
        User user = userRepository.findById(request.getId());
        user.setLoginId(request.getLoginId());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        //user.setProfile(request.getProfile());

        return request.getId();
    }

    /**
     * 회원 삭제
     */
    @Transactional
    @Override
    public int delete(String loginId) {

        return userRepository.delete(loginId);
    }
}
