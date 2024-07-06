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

    //null 허용 X
    @Column(nullable = false, length = 20)
    private String title;

    //null 허용 X
    @Column(nullable = false)
    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    //게시글과 생명주기를 함께하므로 영속화
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    //게시글과 생명주기를 함께하므로 영속화
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emotion> emotions = new ArrayList<>();

    //==연관관계 편의 메서드==//

    //다대일 관계인 사용자 엔티티를 지정하면 해당 사용자 게시글 리스트에 게시글 추가
    public void setAuthor(User user) {
        this.author = user;
        user.getPosts().add(this);
    }
}
