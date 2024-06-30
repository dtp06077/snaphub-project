package project.backend.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.backend.domain.Comment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment) {
        if(comment.getId() == null) {
            em.persist(comment);
        }
        else {
            em.merge(comment);
        }
        return comment.getId();
    }

    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findByPostId(Long id) {
        return em.createQuery("select c from Comment c where c.post.id = :postId", Comment.class)
                .setParameter("postId", id)
                .getResultList();
    }

    public void delete(Long id) {
        Comment comment = em.find(Comment.class, id);
        if(comment != null) {
            em.remove(comment);
        }
    }

}
