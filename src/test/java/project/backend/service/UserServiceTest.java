package project.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.User;
import project.backend.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("HuiSeong");
        user.setLoginId("testLoginId");
        user.setPassword("testPassword");
    }

    @Test
    @DisplayName("회원 가입 테스트")
    public void register_sucess() {
        //Given
        Long userId = userService.register(user);

        //When
        User findUser = userRepository.findById(userId);

        // Then
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success() {
        //Given
        Long userId = userService.register(user);

        //When
        User findUser = userService.login("testLoginId", "testPassword");

        //Then
        assertThat(userRepository.findById(userId)).isEqualTo(findUser);
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 아이디")
    public void login_fail_wrongLoginId() {
        //Given
        Long userId = userService.register(user);

        //When
        try {
            User findUser = userService.login("wrongId", "testPassword");
        } catch (IllegalStateException e) {
            return;
        }

        //Then
        fail("로그인 실패 예외가 발생해야 한다.");
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 비밀번호")
    public void login_fail_wrongPassword() {
        //Given
        Long userId = userService.register(user);

        //When
        try {
            User findUser = userService.login("testLoginId", "wrongPassword");
        } catch (IllegalStateException e) {
            return;
        }

        //Then
        fail("로그인 실패 예외가 발생해야 한다.");
    }
}
