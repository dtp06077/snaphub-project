package project.server.dto.response.post;

import lombok.Getter;
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
    }
}
