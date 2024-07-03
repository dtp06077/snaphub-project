package project.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.Comment;
import project.backend.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 댓글 저장
     */
    @Transactional
    public Long saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * 댓글 검색
     */
    public Comment getComment(Long id) {
        return commentRepository.findById(id);
    }

    /**
     *특정 게시글의 댓글 검색
     */
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }
}
