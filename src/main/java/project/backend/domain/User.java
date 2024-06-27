package project.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
//user는 많은 데이터베이스에서 예약어로 사용되므로, 테이블 이름은 다른 이름으로 변경
@Table(name = "users")
@Getter @Setter
public class User {

    @Id @GeneratedValue
    private Long id;
    private String username;
}
