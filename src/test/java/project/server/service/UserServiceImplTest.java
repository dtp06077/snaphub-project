package project.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.User;
import project.server.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

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
    @DisplayName("회원 가입 성공 테스트")
    public void register_success() {
        //Given
        Long userId = userServiceImpl.register(user);

        //When
        User findUser = userRepository.findById(userId);

        // Then
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success() {
        //Given
        Long userId = userServiceImpl.register(user);

        //When
        User findUser = userServiceImpl.login("testLoginId", "testPassword");

        //Then
        assertThat(userRepository.findById(userId)).isEqualTo(findUser);
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 아이디")
    public void login_fail_wrongLoginId() {
        //Given
        Long userId = userServiceImpl.register(user);

        //When
        try {
            User findUser = userServiceImpl.login("wrongId", "testPassword");
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
        Long userId = userServiceImpl.register(user);

        //When
        try {
            User findUser = userServiceImpl.login("testLoginId", "wrongPassword");
        } catch (IllegalStateException e) {
            return;
        }

        //Then
        fail("로그인 실패 예외가 발생해야 한다.");
    }

    @Test
    @DisplayName("프로필 업데이트 성공 테스트")
    public void updateProfile_success() {
        //Given
        Long userId = userServiceImpl.register(user);

        //When
        userServiceImpl.updateProfile(userId, "abcd@email.com", "A#$!");

        //Then
        assertThat(user.getEmail()).isEqualTo("abcd@email.com");
        assertThat(user.getProfile()).isEqualTo("A#$!");
    }

    @Test
    @DisplayName("프로필 업데이트 실패 테스트")
    public void updateProfile_fail() {
        //Given

        //When
        try {
            userServiceImpl.updateProfile(0L, "abcd@email.com", "A#$!");
        } catch (IllegalStateException e) {
            return;
        }
        //Then
        fail("프로필 업데이트 실패 예외가 발생해야 한다.");
    }
}
