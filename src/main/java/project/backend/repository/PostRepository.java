package project.backend.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.backend.domain.Post;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    //게시글 저장
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

    //게시글 검색
    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    //특정 사용자 게시글 검색
    public List<Post> findByUserId(Long id) {
//        User user = em.find(User.class, id);
//        return user.getPosts();
//        직관적이지만 성능에 영향을 줄 수 있기에 JPQL로 대체
        return em.createQuery("select p from Post p where p.author.id = :userId", Post.class)
                .setParameter("userId", id)
                .getResultList();
    }

    //모든 게시글 검색
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    //게시글 삭제 메서드
    public void delete(Long id) {
        Post post = em.find(Post.class, id);
        if (post != null) {
            em.remove(post);
        }
    }
}
