package project.backend.repository;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import project.backend.domain.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    //사용자 저장
    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }
    //사용자 검색 메서드
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    //닉네임을 통한 사용자 검색 메서드
    public User findByName(String name) {
        try {
            return em.createQuery("select u from User u where u.name = :name",
                            User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    //로그인 아이디를 통한 사용자 검색 메서드
    public User findByLoginId(String loginId) {
        try{
            return em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                    .setParameter("loginId", loginId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; //결과가 없으면 null 반환
        }
    }

    //사용자 삭제 메서드
    public void delete(Long id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }
}
