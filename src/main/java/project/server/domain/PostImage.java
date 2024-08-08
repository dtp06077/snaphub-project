package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.server.domain.id.PostImageId;

@Entity
@Getter @Setter
@Table(name = "post_image")
@IdClass(PostImageId.class)
public class PostImage {

    @Id
    @Column(name = "post_id")
    private int postId;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @Lob
    private String image;
}
