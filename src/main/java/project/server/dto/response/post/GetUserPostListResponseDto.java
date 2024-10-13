package project.server.dto.response.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.domain.Post;
import project.server.dto.object.PostListItem;
import project.server.dto.response.ResponseDto;

import java.util.List;

@Getter
public class GetUserPostListResponseDto extends ResponseDto {

    private List<PostListItem> userPostList;

    private GetUserPostListResponseDto(List<Post> userPostList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userPostList = PostListItem.copyList(userPostList);
    }

    public static ResponseEntity<GetUserPostListResponseDto> success(List<Post> userPostList) {
        GetUserPostListResponseDto result = new GetUserPostListResponseDto(userPostList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
