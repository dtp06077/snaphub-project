package project.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.server.dto.request.post.UploadPostRequestDto;
import project.server.dto.response.post.GetPostResponseDto;
import project.server.dto.response.post.UploadPostResponseDto;
import project.server.security.domain.CustomUser;
import project.server.service.PostService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //특정 게시물 검색
    @GetMapping("/{postId}")
    public ResponseEntity<? super GetPostResponseDto> getPost(
            @PathVariable("postId") int postId) {
        return postService.getPost(postId);
    }

    //게시물 작성
    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<? super UploadPostResponseDto> uploadPost(
            @RequestBody @Valid UploadPostRequestDto request,
            @AuthenticationPrincipal CustomUser customUser
            ) {
        return postService.uploadPost(request, customUser);
    }
}
