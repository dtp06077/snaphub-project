package project.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchLogRepository {

    private final EntityManager em;

    public List<String> findPopularSearch() {
        return em.createQuery("select sl.searchWord from SearchLog sl " +
                "group by sl.searchWord " +
                "order by count(sl.searchWord) desc", String.class)
                .setMaxResults(10)
                .getResultList();
    }
}
