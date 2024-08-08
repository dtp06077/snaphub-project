package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.server.domain.id.UserAuthId;

@Entity
@Table(name = "users_auth")
@IdClass(UserAuthId.class)
@Getter @Setter
public class UserAuth {

    @Id
    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private String auth;

    //연관관계 메서드
    public String getUserLoginId() {
        return this.user.getLoginId();
    }
}
