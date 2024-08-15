package project.server.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinRequestDto {

    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;

    private String profileImage;

    private String telNumber;

    private String address;

    private String addressDetail;
    @NotBlank
    private boolean agreedPersonal;

}
