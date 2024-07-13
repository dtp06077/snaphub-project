package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_auth")
@Getter @Setter
public class Auth {

    @Id
    @GeneratedValue
    @Column(name = "auth_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role")
    private String user;

    private String auth;
}
