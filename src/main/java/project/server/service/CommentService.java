package project.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.Comment;
import project.server.repository.CommentRepository;
import project.server.repository.PostRepository;
import project.server.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    /**
     * 댓글 저장
     */
    @Transactional
    public int saveComment(int userId, int postId, Comment comment) {
        comment.setAuthor(userRepository.findById(userId));
        comment.setPost(postRepository.findById(postId));
        return commentRepository.save(comment);
    }

    /**
     * 댓글 검색
     */
    public Comment getComment(int id) {
        return commentRepository.findById(id);
    }

    /**
     *특정 게시글의 댓글 검색
     */
    public List<Comment> getCommentsByPostId(int postId) {
        return commentRepository.findByPostId(postId);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(int id) {
        commentRepository.delete(id);
    }
}
