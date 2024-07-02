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
    //외래키
    @JoinColumn(name = "user_id")
    private User author;

    //증복 허용 X
    @Column(nullable = false, length = 20)
    private String title;

    //중복 허용 X
    @Column(nullable = false)
    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    //게시글과 생명주기를 함께하므로 cascade
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    //게시글과 생명주기를 함께하므로 cascade
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Emotion> emotions = new ArrayList<>();
}
