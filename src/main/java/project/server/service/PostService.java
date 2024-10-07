package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.post.UpdatePostRequestDto;
import project.server.dto.request.post.UploadPostRequestDto;
import project.server.dto.request.post.WriteCommentRequestDto;
import project.server.dto.response.post.*;
import project.server.security.domain.CustomUser;

public interface PostService {

    //게시물 등록하기 Response
    ResponseEntity<? super UploadPostResponseDto> uploadPost(UploadPostRequestDto request, CustomUser customUser);

    //게시물 삭제 Response
    ResponseEntity<? super DeletePostResponseDto> deletePost(int postId, CustomUser customUser);

    //게시물 수정 Response
    ResponseEntity<? super UpdatePostResponseDto> updatePost(int postId, UpdatePostRequestDto request, CustomUser customUser);

    //특정 게시물 불러오기 Response
    ResponseEntity<? super GetPostResponseDto> getPost(int postId);

    //감정표현 등록하기 Response
    ResponseEntity<? super PutEmotionResponseDto> putEmotion(int postId, String emotionStatus, CustomUser customUser);

    //감정표현 리스트 불러오기 Response
    ResponseEntity<? super GetEmotionsResponseDto> getEmotions(int postId);

    //댓글 등록하기 Response
    ResponseEntity<? super WriteCommentResponseDto> writeComment(WriteCommentRequestDto request, int postId, CustomUser customUser);

    //댓글 리스트 불러오기 Response
    ResponseEntity<? super GetCommentsResponseDto> getComments(int postId);

    //최신 게시물 리스트 불러오기 Response
    ResponseEntity<? super GetLatestPostListResponseDto> getLatestPosts();

    //top3 게시물 리스트 불러오기 Response
    ResponseEntity<? super GetTop3PostListResponseDto> getTop3Posts();

    //게시물 조회수 증가 Response
    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(int postId);
}
