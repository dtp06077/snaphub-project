package project.server.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WriteCommentRequestDto {

    @NotBlank
    private String comment;

}
