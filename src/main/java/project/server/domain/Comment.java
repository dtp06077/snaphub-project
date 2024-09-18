package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.server.dto.request.post.WriteCommentRequestDto;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private int id;

    @Column(nullable = false)
    @Lob
    private String content;

    private String commentDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //생성자
    public Comment(WriteCommentRequestDto request, Post post, User user) {
        this.content = request.getContent();
        this.commentDatetime = LocalDateTime.now().toString();
        setAuthor(user);
        setPost(post);
    }

    //==연관관계 편의 메서드==//

    //다대일 관계인 게시글 엔티티를 지정하면 해당 게시글의 댓글 리스트에 댓글 추가
    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
        post.setCommentCnt(post.getCommentCnt()+1);
    }

    //다대일 관계인 사용자 엔티티를 지정하면 해당 사용자의 댓글 리스트에 댓글 추가
    public void setAuthor(User user) {
        this.author = user;
        user.getComments().add(this);
    }
}
