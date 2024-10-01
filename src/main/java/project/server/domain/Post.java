package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.server.dto.request.post.UpdatePostRequestDto;
import project.server.dto.request.post.UploadPostRequestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    //외래키
    @JoinColumn(name = "user_id")
    private User author;

    @Column(nullable = false)
    private String authorId;

    @Column(nullable = false)
    private String authorName;

    private String authorProfile;

    //자식 엔티티 영속화
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    //null 허용 X
    @Column(nullable = false)
    @Lob
    private String title;

    //null 허용 X
    @Column(nullable = false)
    @Lob
    private String content;

    private String postDatetime;

    //감정표현 카운트 파라미터
    private int happyEmoCnt;
    private int sadEmoCnt;
    private int angryEmoCnt;

    private int commentCnt;

    private int viewCnt;

    //생성자
    public Post(UploadPostRequestDto request, User user) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.postDatetime = LocalDateTime.now().toString();
        this.happyEmoCnt = 0;
        this.sadEmoCnt = 0;
        this.angryEmoCnt = 0;
        this.commentCnt = 0;
        this.viewCnt = 0;
        this.authorId = user.getLoginId();
        this.authorName = user.getName();
        this.authorProfile = user.getProfileImage();
        setAuthor(user);
    }

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

    //PostImage 의 String 이미지를 반환
    public List<String> getImageList() {
        List<String> imageList = new ArrayList<>();

        for(PostImage image : images) {
            imageList.add(image.getImage());
        }

        return imageList;
    }

    //조회수 증가 메서드
    public void addViewCount() {
        this.viewCnt+=1;
    }

    //게시물 수정 메서드
    public void updatePost(UpdatePostRequestDto request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
