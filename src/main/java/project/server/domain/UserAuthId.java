package project.server.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class UserAuthId implements Serializable {
    private Long userId;

    // equals() 및 hashCode() 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // 동일한 인스턴스인지 확인
        if (!(o instanceof UserAuthId)) return false;  // 타입 체크

        UserAuthId that = (UserAuthId) o;  // 타입 캐스팅
        return userId != null && userId.equals(that.userId);  // userId 비교
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
