package project.server.dto.response.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.domain.User;
import project.server.dto.response.ResponseDto;

@Getter
public class GetUserResponseDto extends ResponseDto {

    private String email;
    private String name;
    private String profileImage;

    private GetUserResponseDto(User user) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.email = user.getEmail();
        this.name = user.getName();
        this.profileImage = user.getProfileImage();
    }

    public static ResponseEntity<GetUserResponseDto> success(User user) {
        GetUserResponseDto result = new GetUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
