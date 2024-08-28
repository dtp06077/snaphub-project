package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "post_image")
@NoArgsConstructor
public class PostImage {

    @Id @GeneratedValue
    @Column(name = "post_image_sequence")
    private int sequence;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @Lob
    private String image;

    //생성자
    public PostImage(Post post, String image) {
        this.image = image;
        setPost(post);
    }

    //==연관관계 편의 메서드==//

    //다대일 관계인 사용자 엔티티를 지정하면 해당 사용자 게시글 리스트에 게시글 추가
    public void setPost(Post post) {
        this.post = post;
        post.getImages().add(this);
    }
}
