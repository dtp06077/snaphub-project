package project.server.dto.response.search;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.dto.response.ResponseDto;

import java.util.List;

@Getter
public class GetRelatableSearchListResponseDto extends ResponseDto {

    private List<String> relatableSearchList;

    private GetRelatableSearchListResponseDto(List<String> relatedSearchList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.relatableSearchList = relatedSearchList;
    }

    public static ResponseEntity<GetRelatableSearchListResponseDto> success(List<String> relatedSearchList) {
        GetRelatableSearchListResponseDto result = new GetRelatableSearchListResponseDto(relatedSearchList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
