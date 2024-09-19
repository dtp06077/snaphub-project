package project.server.dto.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.server.domain.Comment;
import project.server.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListItem {
    private String loginId;
    private String name;
    private String profileImage;
    private String commentDateTime;
    private String content;

    public CommentListItem(Comment comment) {
        this.loginId = comment.getAuthor().getLoginId();
        this.name = comment.getAuthor().getName();
        this.profileImage = comment.getAuthor().getProfileImage();
        this.commentDateTime = comment.getCommentDatetime();
        this.content = comment.getContent();
    }

    public static List<CommentListItem> copyList(Post post) {
        List<CommentListItem> commentList = new ArrayList<>();

        for(Comment comment : post.getComments()) {
            CommentListItem item = new CommentListItem(comment);
            commentList.add(item);
        }
        return commentList;
    }
}
