package project.server.dto.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmotionListItem {
    private String loginId;
    private String name;
    private String profileImage;
}
