package project.server.security.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.server.domain.User;
import project.server.dto.CustomUser;
import project.server.repository.UserRepository;

//사용자 인증 방식을 세팅해주기 위한 객체
@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //스프링 시큐리티가 회원의 정보를 읽어들일 때 사용할 로직을 설정하는 메서드
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("login - loadUserByLoginId : " + loginId);

        User user = userRepository.findByLoginId(loginId);

        if( user == null ) {
            log.info("일치하는 아이디가 존재하지 않음.");
            throw new UsernameNotFoundException("회원 아이디를 찾을 수 없습니다 : " + loginId);
        }

        log.info("user : ");
        log.info(user.getName());

        // User -> CustomUser
        CustomUser customUser = new CustomUser(user);

        log.info("customUser : ");
        log.info(customUser.getUsername());

        return customUser;
    }
}
