package project.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.server.domain.SearchLog;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchLogRepository {

    private final EntityManager em;

    //검색어 로그 저장
    public int save(SearchLog searchLog) {
        em.persist(searchLog);
        return searchLog.getSequence();
    }
    //인기 검색어 리스트 검색
    public List<String> findPopularSearch() {
        return em.createQuery("select sl.searchWord from SearchLog sl " +
                "group by sl.searchWord " +
                "order by count(sl.searchWord) desc", String.class)
                .setMaxResults(10)
                .getResultList();
    }

    //연관 검색어 리스트 검색
    public List<String> findRelationSearch(String searchWord) {
        return em.createQuery("select sl.relationWord from SearchLog sl " +
                "where sl.searchWord = :searchWord " +
                "group by sl.relationWord " +
                "order by count(sl.searchWord) desc", String.class)
                .setParameter("searchWord", searchWord)
                .setMaxResults(15)
                .getResultList();
    }
}
