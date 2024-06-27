package project.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//user는 많은 데이터베이스에서 예약어로 사용되므로, 테이블 이름은 다른 이름으로 변경
@Table(name = "users")
@Getter @Setter
public class User {

    @Id @GeneratedValue
    private Long id;

    //회원 이름 필수, 중복 허용 X
    @Column(nullable = false, length = 20, unique = true)
    private String name;

    private String email;

    //로그인 아이디 필수, 중복 허용 X
    @Column(nullable = false, length = 30, unique = true)
    private String loginId;

    //로그인 비밀번호 필수
    @Column(nullable = false, length = 50)
    private String password;

    private String profile;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();
}
