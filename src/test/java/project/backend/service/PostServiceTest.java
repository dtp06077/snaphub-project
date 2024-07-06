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
        user = new User();
        userRepository.save(user);

        post = new Post();
        post.setTitle("title");
        post.setContent("content");
    }

    @Test
    @DisplayName("게시글 저장 성공 테스트")
    public void save_success() {
        //Given
        post.setAuthor(user);
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


}
