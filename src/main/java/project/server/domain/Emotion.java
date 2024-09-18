package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.server.domain.id.EmotionId;

@Entity
@Getter @Setter
@NoArgsConstructor
@IdClass(EmotionId.class)
public class Emotion {

    @Id
    @Column(name = "post_id") // postId를 컬럼으로 매핑
    private int postId;

    @Id
    @Column(name = "user_id") // userId를 컬럼으로 매핑
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmotionStatus status;

    //생성자
    public Emotion(User user, Post post, String status) {
        this.postId = post.getId();
        this.userId = user.getId();
        this.status = EmotionStatus.valueOf(status);
        setPost(post);
        setUser(user);
    }

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

    //게시물 감정표현 감소 메서드
    public void removeEmotion() {
        switch (this.status) {
            case ANGRY -> post.setAngryEmoCnt(post.getAngryEmoCnt()-1);
            case HAPPY -> post.setHappyEmoCnt(post.getHappyEmoCnt()-1);
            case SAD -> post.setSadEmoCnt(post.getSadEmoCnt()-1);
        }
    }
}
