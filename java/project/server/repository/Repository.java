package project.server.repository;

public interface Repository {

    //엔티티 저장
    public Long save(Object entity);

    //엔티티 조회
    public Object findById(Long id);


    // 삭제
    public void remove(Long id);
}
