package project.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")//외래키
    private User author;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<Emotion> emotions = new ArrayList<>();
}
