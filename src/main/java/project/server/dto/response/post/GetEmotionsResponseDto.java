package project.server.dto.response.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.domain.Post;
import project.server.dto.object.EmotionListItem;
import project.server.dto.response.ResponseDto;

import java.util.List;

@Getter
public class GetEmotionsResponseDto extends ResponseDto {

    private List<EmotionListItem> emotionList;

    private GetEmotionsResponseDto(Post post) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.emotionList = EmotionListItem.copyList(post);
    }

    public static ResponseEntity<GetEmotionsResponseDto> success(Post post) {
        GetEmotionsResponseDto result = new GetEmotionsResponseDto(post);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistPost() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
