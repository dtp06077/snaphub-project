package project.server.service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.User;
import project.server.domain.UserAuth;
import project.server.dto.request.auth.JoinRequestDto;
import project.server.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest mockRequest;

    private JoinRequestDto userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new JoinRequestDto();
        userRequest.setName("HuiSeong");
        userRequest.setLoginId("testLoginId");
        userRequest.setPassword("testPassword");
        userRequest.setEmail("");
        //userRequest.setProfile("");
    }

//    @Test
//    @DisplayName("회원 가입 성공 테스트")
//    public void insert_success() throws Exception {
//        //Given
//        int userId = userServiceImpl.insert(userRequest);
//
//        //When
//        User findUser = userRepository.findById(userId);
//        UserAuth auth = findUser.getAuths().get(0);
//
//        // Then
//        System.out.println(findUser.getPassword());
//        assertThat(findUser.getName()).isEqualTo("HuiSeong");
//        assertThat(auth.getAuth()).isEqualTo("ROLE_USER");
//    }

//    @Test
//    @DisplayName("로그인 성공 테스트")
//    public void login_success() throws Exception {
//        //Given
//        Long userId = userServiceImpl.insert(userRequest);
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setLoginId(userRequest.getLoginId());
//        loginRequest.setPassword("testPassword");
//
//        //When
//        userServiceImpl.login(loginRequest, mockRequest);
//
//        // Then
//        User findUser = userRepository.findById(userId);
//        assertThat(findUser).isNotNull();
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
//    public void login_fail_wrong_password() throws Exception {
//        //Given
//        userServiceImpl.insert(userRequest);
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setLoginId(userRequest.getLoginId());
//        loginRequest.setPassword("wrongPassword");
//
//        // When & Then
//        assertThrows(AuthenticationException.class, () -> {
//            userServiceImpl.login(loginRequest, mockRequest);
//        });
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트 - 존재하지 않는 사용자")
//    public void login_fail_nonexistent_user() throws Exception {
//        // Given
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setLoginId("nonexistentUser");
//        loginRequest.setPassword("testPassword");
//
//        // When & Then
//        assertThrows(AuthenticationException.class, () -> {
//            userServiceImpl.login(loginRequest, mockRequest);
//        });
//    }
//
//    @Test
//    @DisplayName("회원 정보 수정 성공 테스트")
//    public void update_success() throws Exception {
//        //Given
//        Long userId = userServiceImpl.insert(userRequest);
//        UserRequest updateRequest = new UserRequest();
//        updateRequest.setLoginId("newId");
//        updateRequest.setPassword("newPw");
//        updateRequest.setEmail("newEmail");
//        updateRequest.setProfile("newPro");
//        updateRequest.setName("newName");
//
//        //When
//        userServiceImpl.update(userId, updateRequest);
//        User user = userRepository.findById(userId);
//
//        //Then
//        assertThat(user.getEmail()).isEqualTo("newEmail");
//        assertThat(user.getProfile()).isEqualTo("newPro");
//        assertThat(user.getLoginId()).isEqualTo("newId");
//    }
}
