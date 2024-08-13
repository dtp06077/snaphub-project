package project.server.dto.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListItem {
    private String loginId;
    private String name;
    private String profileImage;
    private String commentDateTime;
    private String content;
}
