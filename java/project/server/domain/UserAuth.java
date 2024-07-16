package project.server.domain;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String auth;

    //연관관계 메서드
    public String getUserLoginId() {
        return this.user.getLoginId();
    }
}
