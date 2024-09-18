package project.server.dto.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.server.domain.Emotion;
import project.server.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmotionListItem {
    private String loginId;
    private String name;
    private String profileImage;
    private String status;

    public EmotionListItem(Emotion emotion) {
        this.loginId = emotion.getUser().getLoginId();
        this.name = emotion.getUser().getName();
        this.profileImage = emotion.getUser().getProfileImage();
        this.status = emotion.getStatus().toString();
    }

    public static List<EmotionListItem> copyList(Post post) {
        List<EmotionListItem> emotionList = new ArrayList<>();

        for(Emotion emotion : post.getEmotions()) {
            EmotionListItem item = new EmotionListItem(emotion);
            emotionList.add(item);
        }
        return emotionList;
    }
}
