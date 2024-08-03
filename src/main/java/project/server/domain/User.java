package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAuth> auths = new ArrayList<>();

    //회원 이름 필수, 중복 허용 X
    @Column(nullable = false, length = 20, unique = true)
    private String name;

    private String email;

    //로그인 아이디 필수, 중복 허용 X
    @Column(nullable = false, length = 30, unique = true)
    private String loginId;

    //로그인 비밀번호 필수
    @Column(nullable = false, length = 200)
    private String password;

    //프로필 이미지 경로
    private String profile;

    //String 으로 변경
    private String createdAt;

    //자식 엔티티 영속화
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    //자식 엔티티 영속화
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    //자식 엔티티 영속화
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emotion> emotions = new ArrayList<>();

    //연관관계 메서드

    //권한 가져오기
    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        for (UserAuth a : this.auths) {
            roles.add(a.getAuth());
        }
        return roles;
    }

    //게시글 갯수 가져오기
    public int getPostCnt() {
        return this.posts.size();
    }
}
