package project.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.server.domain.Emotion;
import project.server.domain.id.EmotionId;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmotionRepository {

    private final EntityManager em;

    public void save(Emotion emotion) {
        em.persist(emotion);
    }

    public Emotion findById(int postId, int userId) {
        EmotionId emotionId = new EmotionId(postId, userId);
        return em.find(Emotion.class, emotionId);
    }

    public void delete(int postId, int userId) {
        Emotion emotion = findById(postId, userId);
        if(emotion != null) {
            em.remove(emotion);
        }
    }
}
