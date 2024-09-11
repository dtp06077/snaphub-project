package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.post.PostUploadRequestDto;
import project.server.dto.response.post.GetPostResponseDto;
import project.server.dto.response.post.PostUploadResponseDto;
import project.server.security.domain.CustomUser;

public interface PostService {

    //게시물 쓰기 Response
    ResponseEntity<? super PostUploadResponseDto> writePost(PostUploadRequestDto requestDto, CustomUser customUser);

    //특정 게시물 불러오기 Response
    ResponseEntity<? super GetPostResponseDto> getPost(int postId);
}
