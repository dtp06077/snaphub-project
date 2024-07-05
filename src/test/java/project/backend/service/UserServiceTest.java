package project.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.User;
import project.backend.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void register_sucess() {
        //Given
        Long userId = userService.register(user);

        //When
        User findUser = userRepository.findById(userId);

        // Then
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    public void login_success() {
        //Given
        Long userId = userService.register(user);

        //When
        User findUser = userService.login("testLoginId", "testPassword");

        //Then
        assertThat(userRepository.findById(userId)).isEqualTo(findUser);
    }
}
