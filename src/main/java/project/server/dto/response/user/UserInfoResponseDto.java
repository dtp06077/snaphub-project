package project.server.dto.response.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.domain.User;
import project.server.dto.response.ResponseDto;

import java.util.List;

@Getter
public class UserInfoResponseDto extends ResponseDto {

    private int userId;
    private String loginId;
    private String email;
    private String name;
    private String profile;
    private String telNumber;
    private String address;
    private List<String> roles;

    private UserInfoResponseDto(User user) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = user.getId();
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.profile = user.getProfileImage();
        this.telNumber = user.getTelNumber();
        this.address = user.getAddress();
        this.roles = user.getRoles();
    }

    //HTTP Status 200
    public static ResponseEntity<UserInfoResponseDto> success(User user) {
        UserInfoResponseDto result = new UserInfoResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //HTTP Status 401
    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
