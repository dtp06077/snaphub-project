package project.server.dto.response.search;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.dto.response.ResponseDto;

import java.util.List;

@Getter
public class GetPopularSearchListResponseDto extends ResponseDto {

    private List<String> popularSearchList;

    private GetPopularSearchListResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<GetPopularSearchListResponseDto> success() {
        GetPopularSearchListResponseDto result = new GetPopularSearchListResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
