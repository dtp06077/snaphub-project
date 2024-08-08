package project.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.server.domain.Comment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public int save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public Comment findById(int id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findByPostId(int id) {
        return em.createQuery("select c from Comment c where c.post.id = :postId", Comment.class)
                .setParameter("postId", id)
                .getResultList();
    }

    public void delete(int id) {
        Comment comment = em.find(Comment.class, id);
        if(comment != null) {
            em.remove(comment);
        }
    }

}
