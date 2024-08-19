package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_auth")
@Getter @Setter
public class UserAuth {

    @Id
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
