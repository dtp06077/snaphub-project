package project.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.server.dto.request.post.PostUploadRequestDto;
import project.server.dto.response.post.PostUploadResponseDto;
import project.server.security.domain.CustomUser;
import project.server.service.PostService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시물 작성
    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<? super PostUploadResponseDto> writePost(
            @RequestBody @Valid PostUploadRequestDto request,
            @AuthenticationPrincipal CustomUser customUser
            ) {
        return postService.writePost(request, customUser);
    }
}
