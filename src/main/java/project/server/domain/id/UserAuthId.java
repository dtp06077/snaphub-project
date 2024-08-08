package project.server.domain.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class UserAuthId implements Serializable {
    private int userId;

    // equals() 및 hashCode() 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAuthId)) return false;
        UserAuthId that = (UserAuthId) o;
        return userId == that.userId;  // int 비교
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(userId);  // int의 해시 코드
    }
}
