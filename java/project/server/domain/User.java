package project.server.domain;

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
    @Column(name = "user_id") //기본키
    private Long id;

    @Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private String role;

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

    //자식 엔티티 영속화
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    //자식 엔티티 영속화
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    //자식 엔티티 영속화
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emotion> emotions = new ArrayList<>();
}
