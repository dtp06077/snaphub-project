package project.server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_auth")
@Getter @Setter
public class UserAuth {

    @Id
    @GeneratedValue
    @Column(name = "auth_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String auth;

    public UserAuth(String auth) {
        this.auth = auth;
    }

    public UserAuth() {
    }

    //연관관계 메서드
    public String getUserLoginId() {
        return this.user.getLoginId();
    }
}
