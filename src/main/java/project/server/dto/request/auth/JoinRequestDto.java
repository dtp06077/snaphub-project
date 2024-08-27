package project.server.dto.request.auth;

import jakarta.validation.constraints.*;
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
    @Size(min = 7, max = 18)
    private String password;
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String telNumber;

    private String profileImage;

    private String address;
    @NotNull
    private Boolean agreedPersonal;

}
