package project.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.Emotion;
import project.backend.repository.EmotionRepository;
import project.backend.repository.PostRepository;
import project.backend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmotionService {

    private final EmotionRepository emotionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 감정표현 저장
     */
    @Transactional
    public Long saveEmotion(Long userId, Long postId, Emotion emotion) {
        emotion.setUser(userRepository.findById(userId));
        emotion.setPost(postRepository.findById(postId));
        return emotionRepository.save(emotion);
    }

    /**
     * 감정표현 삭제
     */
    @Transactional
    public void deleteEmotion(Long id) {
        emotionRepository.delete(id);
    }

    /**
     * 특정 게시글의 감정표현 검색
     */
    public List<Emotion> getEmosByPostId(Long postId) {
        return emotionRepository.findByPostId(postId);
    }
}
