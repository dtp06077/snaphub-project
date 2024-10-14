package project.server.dto.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.server.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostListItem {

    private int postId;
    private String title;
    private String content;
    private String postDateTime;
    private String postTitleImage;
    private String posterName;
    private String posterProfileImage;
    private int emotionCount;
    private int commentCount;
    private int viewCount;

    public PostListItem(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postDateTime = post.getPostDateTime();
        this.posterName = post.getAuthor().getName();
        this.posterProfileImage = post.getAuthor().getProfileImage();
        this.emotionCount = post.getEmotions().size();
        this.commentCount = post.getCommentCnt();
        this.viewCount = post.getViewCnt();

        if(!post.getImageList().isEmpty()) {
            this.postTitleImage = post.getImageList().get(0);
        }
    }

    public static List<PostListItem> copyList(List<Post> posts) {
        List<PostListItem> postList = new ArrayList<>();

        for(Post post : posts) {
            PostListItem item = new PostListItem(post);
            postList.add(item);
        }
        return postList;
    }
}
