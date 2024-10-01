package project.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.server.domain.PostImage;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostImageRepository {

    private final EntityManager em;

    public void save(PostImage postImage) {
        em.persist(postImage);
    }

    public void saveAll(List<PostImage> postImages) {
        for (PostImage postImage : postImages) {
            save(postImage);
        }
    }

    public void deleteByPostId(int postId) {
        em.createQuery("delete from PostImage p where p.post.id = :postId")
                .setParameter("postId", postId)
                .executeUpdate();
    }
}
