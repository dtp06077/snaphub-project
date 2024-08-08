package project.server.domain.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class PostImageId implements Serializable {
    private int postId;

    // equals() 및 hashCode() 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostImageId)) return false;
        PostImageId that = (PostImageId) o;
        return postId == that.postId;  // int 비교
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(postId);  // int의 해시 코드
    }
}
