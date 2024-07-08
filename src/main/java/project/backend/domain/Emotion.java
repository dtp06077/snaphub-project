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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private EmotionStatus status;

    //==연관관계 편의 메서드==//

    //일대다 관계인 게시글 엔티티를 지정하면 해당 게시글 감정표현 리스트에 감정표현 추가
    public void setPost(Post post) {
        this.post = post;
        int i;
        switch (this.status) {
            case ANGRY -> {
                i = post.getAngryEmoCnt();
                post.setAngryEmoCnt(i++);
            }
            case HAPPY -> {
                i = post.getHappyEmoCnt();
                post.setHappyEmoCnt(i++);
            }
            case SAD -> {
                i = post.getSadEmoCnt();
                post.setSadEmoCnt(i++);
            }
        }
        post.getEmotions().add(this);
    }
    //감정표현한 사용자의 감정표현 리스트에 감정표현 추가
    public void setUser(User user) {
        this.user = user;
        user.getEmotions().add(this);
    }
}
