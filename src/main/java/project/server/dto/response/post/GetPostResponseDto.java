package project.server.dto.response.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.domain.Post;
import project.server.dto.response.ResponseDto;

import java.util.List;

@Getter
public class GetPostResponseDto extends ResponseDto {

    private int postId;
    private String title;
    private String content;
    private List<String> imageList;
    private String postDateTime;
    private String posterName;
    private String posterId;
    private String posterProfileImage;

    private GetPostResponseDto(Post post) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageList = post.getImageList();
        this.postDateTime = post.getPostDateTime();
        this.posterName = post.getAuthor().getName();
        this.posterId = post.getAuthor().getLoginId();
        this.posterProfileImage = post.getAuthor().getProfileImage();
    }

    //Http Status 200
    public static ResponseEntity<GetPostResponseDto> success(Post post) {
        GetPostResponseDto result = new GetPostResponseDto(post);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //HTTP Status 400
    public static ResponseEntity<ResponseDto> noExistPost() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
