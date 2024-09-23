package project.server.dto.response.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.dto.response.ResponseDto;

public class IncreaseViewCountResponseDto extends ResponseDto {

    private IncreaseViewCountResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    //Http Status 200
    public static ResponseEntity<IncreaseViewCountResponseDto> success() {
        IncreaseViewCountResponseDto result = new IncreaseViewCountResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //HTTP Status 400
    public static ResponseEntity<ResponseDto> noExistPost() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
