package project.server.dto.response.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.dto.response.ResponseDto;

@Getter
public class UpdateNameResponseDto extends ResponseDto {

    private UpdateNameResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<UpdateNameResponseDto> success() {
        UpdateNameResponseDto result = new UpdateNameResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> duplicateName() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_NAME, ResponseMessage.DUPLICATE_NAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
