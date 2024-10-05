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
public class GetLatestPostListResponseDto extends ResponseDto {

    private List<PostListItem> latestList;

    private GetLatestPostListResponseDto(List<Post> postList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.latestList = PostListItem.copyList(postList);
    }

    public static ResponseEntity<GetLatestPostListResponseDto> success(List<Post> postList) {
        GetLatestPostListResponseDto result = new GetLatestPostListResponseDto(postList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
