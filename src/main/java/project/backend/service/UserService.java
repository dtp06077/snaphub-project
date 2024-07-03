package project.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.User;
import project.backend.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 등록
     */
    @Transactional
    public Long register(User user) {
        validateDuplicateUser(user); //중복 사용자 검증
        user.setCreatedAt(LocalDateTime.now()); //현재 시간 설정
        return userRepository.save(user);
    }
    /**
     * 중복 사용자 검증
     */
    private void validateDuplicateUser(User user) {
        User findUser = userRepository.findByName(user.getName());
        if(findUser != null) {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        }
    }
    /**
     * 사용자 로그인
     */
    @Transactional
    public User login(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId);
        //로그인 비밀번호와 아이디 불일치시
        if(user == null || !user.getPassword().equals(password)) {
            throw new IllegalStateException("로그인 ID 또는 비밀번호가 잘못되었습니다.");
        }
        return user;
    }
    /**
     * 사용자 프로필 업데이트
     */
    @Transactional
    public void updateProfile(Long userId, String email, String profile) {
        User user = userRepository.findById(userId);
        if(user == null) {
            throw new IllegalStateException("해당 사용자가 존재하지 않습니다.");
        }
        user.setEmail(email);
        user.setProfile(profile);
    }
}
