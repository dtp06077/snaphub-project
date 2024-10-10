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
public class GetSearchPostListResponseDto extends ResponseDto {

    private List<PostListItem> searchList;

    private GetSearchPostListResponseDto(List<Post> searchList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.searchList = PostListItem.copyList(searchList);
    }

    public static ResponseEntity<GetSearchPostListResponseDto> success(List<Post> searchList) {
        GetSearchPostListResponseDto result = new GetSearchPostListResponseDto(searchList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
