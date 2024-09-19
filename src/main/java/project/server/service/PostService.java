package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.post.UploadPostRequestDto;
import project.server.dto.request.post.WriteCommentRequestDto;
import project.server.dto.response.post.*;
import project.server.security.domain.CustomUser;

public interface PostService {

    //게시물 등록하기 Response
    ResponseEntity<? super UploadPostResponseDto> uploadPost(UploadPostRequestDto requestDto, CustomUser customUser);

    //특정 게시물 불러오기 Response
    ResponseEntity<? super GetPostResponseDto> getPost(int postId);

    //감정표현 등록하기 Response
    ResponseEntity<? super PutEmotionResponseDto> putEmotion(int postId, String emotionStatus, CustomUser customUser);

    //감정표현 리스트 불러오기 Response
    ResponseEntity<? super GetEmotionsResponseDto> getEmotions(int postId);

    //댓글 등록하기 Response
    ResponseEntity<? super WriteCommentResponseDto> writeComment(WriteCommentRequestDto requestDto, int postId, CustomUser customUser);

    //댓글 리스트 불러오기 Response
    ResponseEntity<? super GetCommentsResponseDto> getComments(int postId);
}
