package project.backend.db;

import project.backend.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.backend.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataBaseTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testUser() {

        User user = new User();
        user.setUsername("userA");
        Long saveId = userRepository.save(user);

        User findUser = userRepository.find(saveId);

        //영속성 컨텍스트로 저장된 유저와 조회된 유저의 값이 일치하는지 확인
        assertEquals(findUser.getId(), user.getId());
        assertEquals(findUser.getUsername(), user.getUsername());
        assertEquals(findUser, user);
    }

}
