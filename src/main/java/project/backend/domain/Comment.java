package project.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //==연관관계 편의 메서드==//

    //다대일 관계인 게시글 엔티티를 지정하면 해당 게시글의 댓글 리스트에 댓글 추가
    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    //다대일 관계인 사용자 엔티티를 지정하면 해당 사용자의 댓글 리스트에 댓글 추가
    public void setUser(User user) {
        this.author = user;
        user.getComments().add(this);
    }
}
