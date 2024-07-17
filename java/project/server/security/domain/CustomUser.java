package project.server.security.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.server.domain.User;
import project.server.domain.UserAuth;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUser implements UserDetails {

    private final User user;

    /**
     * 권한 getter 메소드
     * List<UserAuth> --> Collection<SimpleGrantedAuthority> (auth)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserAuth> authList = user.getAuths(); //UserAuth (authId, userId, auth)

        // SimpleGrantedAuthority() - "ROLE_USER"
        Collection<SimpleGrantedAuthority> roleList = authList.stream()
                                        .map((auth) -> new SimpleGrantedAuthority(auth.getAuth()))
                                        .collect(Collectors.toList());

        return roleList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
