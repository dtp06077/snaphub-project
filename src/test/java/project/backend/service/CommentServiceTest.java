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

    @Test
    @DisplayName("특정 게시글 댓글 검색 성공 테스트")
    public void byPostId_success() {
        //Given
        User user2 = makeUser("gildong", "2345", "2345");
        Long userId2 = userService.register(user2);
        Post post2 = makePost("title2", "content2");
        Post post3 = makePost("title3", "content3");
        Long postId2 = postService.savePost(userId2, post2);
        Long postId3 = postService.savePost(userId, post3);

        Comment comment1 = makeComment("안녕");
        Comment comment2 = makeComment("하이");
        Comment comment3 = makeComment("방가");
        Comment comment4 = makeComment("헬로");

        commentService.saveComment(userId, postId2, comment1);
        commentService.saveComment(userId, postId2, comment2);
        commentService.saveComment(userId2, postId, comment3);
        commentService.saveComment(userId2, postId3, comment4);

        //When
        List<Comment> comments1 = commentService.getCommentsByPostId(postId);
        List<Comment> comments2 = commentService.getCommentsByPostId(postId2);
        List<Comment> comments3 = commentService.getCommentsByPostId(postId3);

        //Then
        assertThat(comments1.get(0)).isEqualTo(comment3);
        assertThat(comments2.get(0)).isEqualTo(comment1);
        assertThat(comments2.get(1)).isEqualTo(comment2);
        assertThat(comments3.get(0)).isEqualTo(comment4);
    }

    @Test
    @DisplayName("댓글 삭제 성공 테스트")
    public void delete_success() {
        //Given
        Comment comment = makeComment("안녕하세요");
        Long commentId = commentService.saveComment(userId, postId, comment);

        //When
        commentService.deleteComment(commentId);

        //Then
        assertThat(commentRepository.findById(commentId)).isNull();
    }

    @Test
    @DisplayName("댓글 연쇄 삭제 성공 테스트 - 게시글")
    public void deletePost_success() {
        //Given
        Comment comment = makeComment("안녕하세요");
        Long commentId = commentService.saveComment(userId, postId, comment);

        //When
        postService.deletePost(postId);

        //Then
        assertThat(commentRepository.findById(commentId)).isNull();
    }

    @Test
    @DisplayName("댓글 연쇄 삭제 성공 테스트 - 사용자")
    public void deleteUser_success() {
        //Given
        Comment comment = makeComment("안녕하세요");
        Long commentId = commentService.saveComment(userId, postId, comment);

        //When
        userService.deleteUser(userId);

        //Then
        assertThat(commentRepository.findById(commentId)).isNull();
    }
}
