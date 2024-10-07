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
public class GetTop3PostListResponseDto extends ResponseDto {

    private List<PostListItem> top3List;

    private GetTop3PostListResponseDto(List<Post> top3List) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.top3List = PostListItem.copyList(top3List);
    }

    public static ResponseEntity<GetTop3PostListResponseDto> success(List<Post> top3List) {
        GetTop3PostListResponseDto result = new GetTop3PostListResponseDto(top3List);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
