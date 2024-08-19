package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "post_image")
public class PostImage {

    @Id @GeneratedValue
    @Column(name = "post_image_sequence")
    private int sequence;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @Lob
    private String image;
}
