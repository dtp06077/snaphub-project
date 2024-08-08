package project.server.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class EmotionId implements Serializable {
    private Long postId;
    private Long userId;

    // equals() 및 hashCode() 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmotionId)) return false;
        EmotionId that = (EmotionId) o;
        return postId.equals(that.postId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return 31 * postId.hashCode() + userId.hashCode();
    }
}
