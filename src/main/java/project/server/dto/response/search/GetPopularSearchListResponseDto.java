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

    private GetPopularSearchListResponseDto(List<String> popularSearchList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.popularSearchList = popularSearchList;
    }

    public static ResponseEntity<GetPopularSearchListResponseDto> success(List<String> popularSearchList) {
        GetPopularSearchListResponseDto result = new GetPopularSearchListResponseDto(popularSearchList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
