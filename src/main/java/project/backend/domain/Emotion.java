package project.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Emotion {

    @Id @GeneratedValue
    @Column(name = "emotion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private EmotionStatus status;

    //==연관관계 편의 메서드==//

    //일대다 관계인 게시글 엔티티를 지정하면 해당 게시글 감정표현 리스트에 감정표현 추가
    public void setPost(Post post) {
        this.post = post;
        post.getEmotions().add(this);
    }
}
