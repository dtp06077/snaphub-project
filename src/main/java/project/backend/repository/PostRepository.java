package project.backend.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.backend.domain.Post;
import project.backend.domain.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post) {
        if (post.getId() == null) {
            em.persist(post);
        }
        else {
            //변경 감지
            em.merge(post);
        }
        return post.getId();
    }

    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findByUserId(Long id) {
//        User user = em.find(User.class, id);
//        return user.getPosts();
//        직관적이지만 성능에 영향을 줄 수 있기에 JPQL로 대체
        return em.createQuery("select p from Post p where p.user.id = :userId", Post.class)
                .setParameter("userId", id)
                .getResultList();
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public void delete(Long id) {
        Post post = em.find(Post.class, id);
        if (post != null) {
            em.remove(post);
        }
    }
}
