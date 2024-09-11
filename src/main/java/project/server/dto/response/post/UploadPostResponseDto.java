package project.server.dto.response.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.dto.response.ResponseDto;

@Getter
public class UploadPostResponseDto extends ResponseDto {

    private UploadPostResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    //Http Status 200
    public static ResponseEntity<UploadPostResponseDto> success() {
        UploadPostResponseDto result = new UploadPostResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //HTTP Status 401
    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
