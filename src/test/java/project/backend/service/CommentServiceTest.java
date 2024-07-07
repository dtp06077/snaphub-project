package project.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.Comment;
import project.backend.domain.Post;
import project.backend.domain.User;
import project.backend.repository.CommentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired private CommentService commentService;

    @Autowired private CommentRepository commentRepository;

    @Autowired private UserService userService;

    @Autowired private PostService postService;

    private User user;
    private Post post;
    private Long userId;
    private Long postId;

    @BeforeEach
    void setUp() {
        user = makeUser("huiseong", "1234", "1234");
        userId = userService.register(user);
        post = makePost("title", "content");
        postId = postService.savePost(userId, post);
    }

    //user 생성 메서드
    User makeUser(String name, String loginId, String password) {
        User user = new User();
        user.setName(name);
        user.setLoginId(loginId);
        user.setPassword(password);

        return user;
    }

    //post 생성 메서드
    Post makePost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        return post;
    }

    //comment 생성 메서드
    Comment makeComment(String content) {
        Comment comment = new Comment();
        comment.setContent(content);

        return comment;
    }

    @Test
    @DisplayName("댓글 저장 성공 테스트")
    public void save_success() {
        //Given
        Comment comment = makeComment("안녕하세요.");
        Long commentId = commentService.saveComment(userId, postId, comment);

        //When
        List<Comment> userComments = user.getComments();
        List<Comment> postComments = post.getComments();
        Comment findComment = commentRepository.findById(commentId);

        //Then
        //사용자 댓글 리스트에 저장된 댓글이 동일한지
        assertThat(userComments.get(0)).isEqualTo(findComment);
        //게시글 댓글 리스트에 저장된 댓글이 동일한지
        assertThat(postComments.get(0)).isEqualTo(findComment);
        //댓글 리포지토리에 저장된 댓글이 동일한지
        assertThat(findComment).isEqualTo(comment);
    }

    @Test
    @DisplayName("댓글 검색 성공 테스트")
    public void getComment_success() {
        //Given
        Comment comment1 = makeComment("안녕하세요.");
        Comment comment2 = makeComment("반갑습니다.");
        Long id1 = commentService.saveComment(userId, postId, comment1);
        Long id2 = commentService.saveComment(userId, postId, comment2);

        //When
        Comment findComment = commentService.getComment(id2);

        //Then
        assertThat(findComment).isEqualTo(comment2);
        assertThat(findComment).isNotEqualTo(comment1);
    }
}
