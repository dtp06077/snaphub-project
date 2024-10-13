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

    private GetLatestPostListResponseDto(List<Post> latestList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.latestList = PostListItem.copyList(latestList);
    }

    public static ResponseEntity<GetLatestPostListResponseDto> success(List<Post> latestList) {
        GetLatestPostListResponseDto result = new GetLatestPostListResponseDto(latestList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}