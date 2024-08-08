package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.server.domain.id.PostImageId;

@Entity
@Getter @Setter
@IdClass(PostImageId.class)
public class PostImage {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    private String image;
}
