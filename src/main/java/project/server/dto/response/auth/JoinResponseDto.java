package project.server.dto.response.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.dto.response.ResponseDto;

@Getter
public class JoinResponseDto extends ResponseDto {

    private JoinResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
    //HTTP Status 200
    public static ResponseEntity<JoinResponseDto> success() {
        JoinResponseDto result = new JoinResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //HTTP Status 400
    public static ResponseEntity<ResponseDto> duplicateId() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> missingId() {
        ResponseDto result = new ResponseDto(ResponseCode.MISSING_ID, ResponseMessage.MISSING_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> missingName() {
        ResponseDto result = new ResponseDto(ResponseCode.MISSING_NAME, ResponseMessage.MISSING_NAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> missingPassword() {
        ResponseDto result = new ResponseDto(ResponseCode.MISSING_PASSWORD, ResponseMessage.MISSING_PASSWORD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> duplicateName() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_NAME, ResponseMessage.DUPLICATE_NAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
