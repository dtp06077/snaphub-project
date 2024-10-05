package project.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.server.dto.request.post.UpdatePostRequestDto;
import project.server.dto.request.post.UploadPostRequestDto;
import project.server.dto.request.post.WriteCommentRequestDto;
import project.server.dto.response.post.*;
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

    //게시물 수정
    @PatchMapping("/{postId}")
    @Secured("ROLE_USER")
    public ResponseEntity<? super UpdatePostResponseDto> updatePost(
            @PathVariable("postId") int postId,
            @RequestBody @Valid UpdatePostRequestDto request,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        return postService.updatePost(postId, request, customUser);
    }

    //게시물 삭제
    @DeleteMapping("/{postId}")
    @Secured("ROLE_USER")
    public ResponseEntity<? super DeletePostResponseDto> deletePost(
            @PathVariable("postId") int postId,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        return postService.deletePost(postId, customUser);
    }

    //감정표현 등록
    @PutMapping("/{postId}/emotion")
    @Secured("ROLE_USER")
    public ResponseEntity<? super PutEmotionResponseDto> putEmotion(
            @PathVariable("postId") int postId,
            @RequestParam String status,
            @AuthenticationPrincipal CustomUser customUser
            ) {
        return postService.putEmotion(postId, status, customUser);
    }

    //감정표현리스트 검색
    @GetMapping("/{postId}/emotion-list")
    public ResponseEntity<? super GetEmotionsResponseDto> getEmotions(
            @PathVariable("postId") int postId
    ) {
        return postService.getEmotions(postId);
    }

    //댓글 작성
    @PostMapping("/{postId}/comment")
    public ResponseEntity<? super WriteCommentResponseDto> writeComment(
            @RequestBody @Valid WriteCommentRequestDto request,
            @PathVariable("postId") int postId,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        return postService.writeComment(request, postId, customUser);
    }

    //댓글리스트 검색
    @GetMapping("/{postId}/comment-list")
    public ResponseEntity<? super GetCommentsResponseDto> getComments(
            @PathVariable("postId") int postId
    ) {
        return postService.getComments(postId);
    }

    @GetMapping("/{postId}/increase-view-count")
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(
            @PathVariable("postId") int postId
    ) {
        return postService.increaseViewCount(postId);
    }

}
