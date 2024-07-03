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

    @Transactional
    public Long saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }
}
