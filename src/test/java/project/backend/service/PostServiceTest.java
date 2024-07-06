package project.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.Post;
import project.backend.domain.User;
import project.backend.repository.PostRepository;
import project.backend.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    private Post post;
    private User user;

    @BeforeEach
    void setUp() {
        user = makeUser("huiseong", "1234", "1234");
        userRepository.save(user);

        post = makePost("title", "content");
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
        Long postId = postService.savePost(user.getId(), post);

        //When
        User findUser = userRepository.findById(user.getId());
        List<Post> postList = findUser.getPosts();
        Post findPost = postRepository.findById(postId);

        //Then
        //사용자 게시글 리스트에 저장된 게시글이 동일한지
        assertThat(postList.get(0)).isEqualTo(post);
        //게시글 리포지토리에 저장된 게시글이 동일한지
        assertThat(findPost).isEqualTo(post);
    }

    @Test
    @DisplayName("게시글 검색 성공 테스트")
    public void getPost_success() {
        //Given
        Long postId = postService.savePost(user.getId(), post);

        //When
        Post findPost = postService.getPost(postId);

        //Then
        assertThat(findPost).isEqualTo(post);
    }

    @Test
    @DisplayName("특정 사용자의 게시글 검색 성공 테스트")
    public void getPostByUser_success() {
        //Given
        Post post2 = makePost("title2", "content2");

        Long postId = postService.savePost(user.getId(), post);
        Long secPostId = postService.savePost(user.getId(), post2);

        //When
        List<Post> posts = postService.getPostByUserId(user.getId());

        //Then
        assertThat(posts.get(0)).isEqualTo(postRepository.findById(postId));
        assertThat(posts.get(1)).isEqualTo(postRepository.findById(secPostId));
    }

    @Test
    @DisplayName("모든 게시글 검색 성공 테스트")
    public void getAllPosts_Success() {
        //Given
        User user2 = new User();
        user2.setName("gildong");
        user2.setLoginId("5678");
        user2.setPassword("5678");
        userRepository.save(user2);

        Post secPost = new Post();
        secPost.setTitle("title2");
        secPost.setContent("content2");
        post.setAuthor(user);
        secPost.setAuthor(user);



        //When

        //Then
    }

}
