package project.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.server.domain.Post;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    //게시물 저장
    public int save(Post post) {
        em.persist(post);
        return post.getId();
    }

    //게시물 검색
    public Post findById(int id) {
        return em.find(Post.class, id);
    }

    //특정 사용자 게시물 검색
    public List<Post> findByUserId(int id) {
//        User user = em.find(User.class, id);
//        return user.getPosts();
//        직관적이지만 성능에 영향을 줄 수 있기에 JPQL로 대체
        return em.createQuery("select p from Post p where p.author.id = :authorId", Post.class)
                .setParameter("authorId", id)
                .getResultList();
    }

    //모든 게시물 검색
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    //최신순으로 게시물 검색
    public List<Post> findRecentPosts(int limit) {
        return em.createQuery("select p from Post p order by p.postDatetime desc", Post.class)
                .setMaxResults(limit)
                .getResultList();
    }

    //게시글 삭제 메서드
    public void delete(int id) {
        Post post = em.find(Post.class, id);
        if (post != null) {
            em.remove(post);
        }
    }
}
