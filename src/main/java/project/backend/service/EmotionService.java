package project.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.Emotion;
import project.backend.repository.EmotionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmotionService {

    private final EmotionRepository emotionRepository;

    /**
     * 감정표현 저장
     */
    @Transactional
    public Long saveEmotion(Emotion emotion) {
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
