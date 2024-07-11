package project.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.Post;
import project.backend.repository.PostRepository;
import project.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    /**
     * 게시글 저장
     */
    @Transactional
    public Long savePost(Long userId, Post post) {
        post.setAuthor(userRepository.findById(userId));
        return postRepository.save(post);
    }
    /**
     * 게시글 검색
     */
    public Post getPost(Long id) {
        return postRepository.findById(id);
    }
    /**
     * 특정 사용자의 게시글 검색
     */
    public List<Post> getPostByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    /**
     * 모든 게시글 검색
     */
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long id) {
        postRepository.delete(id);
    }
    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long id, String title, String content,
                           String imageUrl, LocalDateTime createdAt) {
        Post post = postRepository.findById(id);
        if(post == null) {
            throw new IllegalStateException("해당 게시글이 존재하지 않습니다.");
        }
        post.setTitle(title);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        post.setCreatedAt(createdAt);
    }
}
