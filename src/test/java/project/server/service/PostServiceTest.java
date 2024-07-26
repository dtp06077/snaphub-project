package project.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.Post;
import project.server.domain.User;
import project.server.repository.PostRepository;
import project.server.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private Post post1;
    private User user1;
    private Long postId1;
    private Long userId1;

    @BeforeEach
    void setUp() {
        user1 = makeUser("huiseong", "1234", "1234");
        userId1 = userRepository.userSave(user1);

        post1 = makePost("title", "content");
        postId1 = postService.savePost(user1.getId(), post1);
    }

    //post 생성 메서드
    Post makePost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        return post;
    }

    //user 생성 메서드
    User makeUser(String name, String loginId, String password) {
        User user = new User();
        user.setName(name);
        user.setLoginId(loginId);
        user.setPassword(password);

        return user;
    }
    @Test
    @DisplayName("게시글 저장 성공 테스트")
    public void save_success() {
        //Given

        //When
        User findUser = userRepository.findById(userId1);
        List<Post> postList = findUser.getPosts();
        Post findPost = postRepository.findById(postId1);

        //Then
        //사용자 게시글 리스트에 저장된 게시글이 동일한지
        assertThat(postList.get(0)).isEqualTo(post1);
        //게시글 리포지토리에 저장된 게시글이 동일한지
        assertThat(findPost).isEqualTo(post1);
    }

    @Test
    @DisplayName("게시글 검색 성공 테스트")
    public void getPost_success() {
        //Given

        //When
        Post findPost = postService.getPost(postId1);

        //Then
        assertThat(findPost).isEqualTo(post1);
    }

    @Test
    @DisplayName("특정 사용자의 게시글 검색 성공 테스트")
    public void getPostByUser_success() {
        //Given
        Post post2 = makePost("title2", "content2");
        Long postId2 = postService.savePost(user1.getId(), post2);

        //When
        List<Post> posts = postService.getPostByUserId(user1.getId());

        //Then
        assertThat(posts.get(0)).isEqualTo(postRepository.findById(postId1));
        assertThat(posts.get(1)).isEqualTo(postRepository.findById(postId2));
    }

    @Test
    @DisplayName("모든 게시글 검색 성공 테스트")
    public void getAllPosts_Success() {
        //Given
        User user2 = makeUser("gildong", "2345", "2345");
        User user3 = makeUser("gilson", "3456", "3456");
        userRepository.userSave(user2);
        userRepository.userSave(user3);

        Post post2 = makePost("title2", "content2");
        Post post3 = makePost("title3", "content3");
        Post post4 = makePost("title4", "content4");

        Long postId2 = postService.savePost(user2.getId(), post2);
        Long postId3 = postService.savePost(user3.getId(), post3);
        Long postId4 = postService.savePost(user3.getId(), post4);

        //When
        List<Post> allPosts = postService.getAllPosts();

        //Then
        assertThat(allPosts.get(0)).isEqualTo(postRepository.findById(postId1));
        assertThat(allPosts.get(1)).isEqualTo(postRepository.findById(postId2));
        assertThat(allPosts.get(2)).isEqualTo(postRepository.findById(postId3));
        assertThat(allPosts.get(3)).isEqualTo(postRepository.findById(postId4));
    }

    @Test
    @DisplayName("게시글 삭제 성공 테스트")
    public void deletePost_success() {
        //Given

        //When
        postService.deletePost(postId1);

        //Then
        assertThat(postRepository.findById(postId1)).isNull();
    }

    @Test
    @DisplayName("사용자 삭제 시 게시글 연쇄 삭제 성공 테스트")
    public void deleteUser_success() {
        //Given

        //When
        //userRepository.delete(user1.getId());

        //Then
        assertThat(postRepository.findById(postId1)).isNull();
    }

    @Test
    @DisplayName("게시글 업데이트 성공 테스트")
    public void updatePost_success() {
        //Given
        LocalDateTime now = LocalDateTime.now();

        //When
        postService.updatePost(postId1, "newTitle", "newContent",
                "image", now);

        //Then
        Post findPost = postRepository.findById(postId1);
        assertThat(findPost.getTitle()).isEqualTo("newTitle");
        assertThat(findPost.getContent()).isEqualTo("newContent");
        assertThat(findPost.getImageUrl()).isEqualTo("image");
        assertThat(findPost.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("게시글 업데이트 실패 테스트")
    public void updatePost_fail() {
        //Given
        postRepository.delete(postId1);
        LocalDateTime now = LocalDateTime.now();

        //When
        try {
            postService.updatePost(postId1, "newTitle", "newContent",
                    "image", now);
        } catch (IllegalStateException e) {
            return;
        }

        //Then
        fail("게시글 부재 예외 처리가 발생해야 한다.");
    }

}
