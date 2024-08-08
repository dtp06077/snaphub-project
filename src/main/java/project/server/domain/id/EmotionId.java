package project.server.domain.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class EmotionId implements Serializable {
    private int postId;
    private int userId;

    // equals() 및 hashCode() 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 동일한 인스턴스 체크
        if (!(o instanceof EmotionId)) return false; // 타입 체크
        EmotionId that = (EmotionId) o; // 캐스팅
        return postId == that.postId && userId == that.userId; // int 비교
    }

    @Override
    public int hashCode() {
        return 31 * Integer.hashCode(postId) + Integer.hashCode(userId); // int의 해시 코드
    }
}
