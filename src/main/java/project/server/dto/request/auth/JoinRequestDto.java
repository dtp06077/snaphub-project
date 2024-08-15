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

    private String profileImage;
    @Pattern(regexp = "^[0-9]{11,13}$")
    private String telNumber;

    private String address;

    private String addressDetail;
    @NotNull @AssertTrue
    private Boolean agreedPersonal;

}
