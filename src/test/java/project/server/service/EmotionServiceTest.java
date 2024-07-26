package project.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.Emotion;
import project.server.domain.EmotionStatus;
import project.server.domain.Post;
import project.server.domain.User;
import project.server.repository.EmotionRepository;
import project.server.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class EmotionServiceTest {

    @Autowired private EmotionService emotionService;

    @Autowired private EmotionRepository emotionRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private PostService postService;

    private User user;
    private Post post;
    private Long userId;
    private Long postId;

    @BeforeEach
    void setUp() throws Exception {
        user = makeUser("huiseong", "1234", "1234");
        userId = userRepository.userSave(user);
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

    //emotion 생성 메서드
    Emotion makeEmotion(EmotionStatus status) {
        Emotion emotion = new Emotion();
        emotion.setStatus(status);

        return emotion;
    }

    @Test
    @DisplayName("감정표현 저장 성공 테스트")
    public void save_success() {
        //Given
        User user2 = makeUser("gildong", "2345", "2345");
        userRepository.userSave(user2);
        User user3 = makeUser("gapsu", "3456", "3456");
        userRepository.userSave(user3);

        Emotion emotion1 = makeEmotion(EmotionStatus.ANGRY);
        Long id1 = emotionService.saveEmotion(user2.getId(), postId, emotion1);

        Emotion emotion2 = makeEmotion(EmotionStatus.ANGRY);
        Long id2 = emotionService.saveEmotion(user3.getId(), postId, emotion2);

        Emotion emotion3 = makeEmotion(EmotionStatus.HAPPY);
        Long id3 = emotionService.saveEmotion(user3.getId(), postId, emotion3);

        //When
        Emotion findEmo1 = emotionRepository.findById(id1);
        Emotion findEmo2 = emotionRepository.findById(id2);
        Emotion findEmo3 = emotionRepository.findById(id3);

        List<Emotion> EmoByUser2 = user2.getEmotions();
        List<Emotion> EmoByUser3 = user3.getEmotions();

        //Then
        //저장된 감정표현이 같은지
        assertThat(findEmo1).isEqualTo(emotion1);
        assertThat(findEmo2).isEqualTo(emotion2);
        assertThat(findEmo3).isEqualTo(emotion3);

        //각 사용자의 감정표현 리스트에 저장되었는지
        assertThat(EmoByUser2.get(0)).isEqualTo(emotion1);
        assertThat(EmoByUser3.get(0)).isEqualTo(emotion2);
        assertThat(EmoByUser3.get(1)).isEqualTo(emotion3);

        //게시글에 표현된 감정표현의 개수가 정확한지
        assertThat(post.getAngryEmoCnt()).isEqualTo(2);
        assertThat(post.getHappyEmoCnt()).isEqualTo(1);
        assertThat(post.getSadEmoCnt()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글의 감정표현 검색 성공 테스트")
    public void byPostId_success() {
        //Given
        User user2 = makeUser("gildong", "2345", "2345");
        userRepository.userSave(user2);
        User user3 = makeUser("gapsu", "3456", "3456");
        userRepository.userSave(user3);

        Emotion emotion1 = makeEmotion(EmotionStatus.ANGRY);
        Long id1 = emotionService.saveEmotion(user2.getId(), postId, emotion1);

        Emotion emotion2 = makeEmotion(EmotionStatus.ANGRY);
        Long id2 = emotionService.saveEmotion(user3.getId(), postId, emotion2);

        Emotion emotion3 = makeEmotion(EmotionStatus.HAPPY);
        Long id3 = emotionService.saveEmotion(user3.getId(), postId, emotion3);

        //When
        List<Emotion> emotions = emotionService.getEmosByPostId(postId);

        //Then
        assertThat(emotions).isEqualTo(post.getEmotions());
    }

    @Test
    @DisplayName("감정표현 삭제 성공 테스트")
    public void delete_success() {
        //Given
        User user2 = makeUser("gildong", "2345", "2345");
        userRepository.userSave(user2);

        Emotion emotion1 = makeEmotion(EmotionStatus.ANGRY);
        Long id1 = emotionService.saveEmotion(user2.getId(), postId, emotion1);

        //When
        emotionService.deleteEmotion(id1);

        //Then
        assertThat(emotionRepository.findById(id1)).isNull();
    }

    @Test
    @DisplayName("감정표현 연쇄 삭제 성공 테스트 - 게시글")
    public void deletePost_success() {
        //Given
        User user2 = makeUser("gildong", "2345", "2345");
        userRepository.userSave(user2);

        Emotion emotion1 = makeEmotion(EmotionStatus.ANGRY);
        Long id1 = emotionService.saveEmotion(user2.getId(), postId, emotion1);

        //When
        postService.deletePost(postId);

        //Then
        assertThat(emotionRepository.findById(id1)).isNull();
    }

    @Test
    @DisplayName("감정표현 연쇄 삭제 성공 테스트 - 사용자")
    public void deleteUser_success() {
        //Given
        User user2 = makeUser("gildong", "2345", "2345");
        userRepository.userSave(user2);

        Emotion emotion1 = makeEmotion(EmotionStatus.ANGRY);
        Long id1 = emotionService.saveEmotion(user2.getId(), postId, emotion1);

        //When
        //userRepository.delete(userId);

        //Then
        assertThat(emotionRepository.findById(id1)).isNull();
    }
}
