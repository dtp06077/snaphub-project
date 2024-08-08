package project.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.server.domain.Emotion;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmotionRepository {

    private final EntityManager em;

    public int save(Emotion emotion) {
        em.persist(emotion);
        return emotion.getPost().getId();
    }

    public Emotion findById(int id) {
        return em.find(Emotion.class, id);
    }

    public List<Emotion> findByPostId(int id) {
        return em.createQuery("select e from Emotion e where e.post.id = :postId", Emotion.class)
                .setParameter("postId", id)
                .getResultList();
    }

    public void delete(int id) {
        Emotion emotion = em.find(Emotion.class, id);
        if(emotion != null) {
            em.remove(emotion);
        }
    }
}
