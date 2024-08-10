package project.server.dto.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostListItem {

    private int postId;
    private String title;
    private String content;
    private String postDateTime;
    private String posterName;
    private String posterProfileImage;
    private int emotionCount;
    private int commentCount;
    private int viewCount;
}
