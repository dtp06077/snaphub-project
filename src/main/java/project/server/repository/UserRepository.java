package project.server.repository;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import project.server.domain.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import project.server.domain.UserAuth;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    //사용자 저장
    public int userSave(User user) {
        em.persist(user);
        return user.getId();
    }

    //권한 저장
    public int authSave(UserAuth userAuth) {
        em.persist(userAuth);
        return userAuth.getUser().getId();
    }

    //회원 조회 메서드
    public User findById(int id) {
        return em.find(User.class, id);
    }

    //닉네임을 통한 회원 조회 메서드
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
    public int delete(String loginId) {
        User user = findByLoginId(loginId);
        int id = user.getId();

        if(user != null) {
            em.remove(user);
        }
        return id;
    }
}
