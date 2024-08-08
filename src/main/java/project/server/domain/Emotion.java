package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(EmotionId.class)
public class Emotion {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmotionStatus status;

    //==연관관계 편의 메서드==//

    //일대다 관계인 게시글 엔티티를 지정하면 해당 게시글 감정표현 리스트에 감정표현 추가
    public void setPost(Post post) {
        this.post = post;
        switch (this.status) {
            case ANGRY -> post.setAngryEmoCnt(post.getAngryEmoCnt()+1);
            case HAPPY -> post.setHappyEmoCnt(post.getHappyEmoCnt()+1);
            case SAD -> post.setSadEmoCnt(post.getSadEmoCnt()+1);
        }
        post.getEmotions().add(this);
    }
    //감정표현한 사용자의 감정표현 리스트에 감정표현 추가
    public void setUser(User user) {
        this.user = user;
        user.getEmotions().add(this);
    }
}
