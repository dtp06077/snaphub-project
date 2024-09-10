package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.post.PostUploadRequestDto;
import project.server.dto.response.post.PostUploadResponseDto;
import project.server.security.domain.CustomUser;

public interface PostService {
    ResponseEntity<? super PostUploadResponseDto> writePost(PostUploadRequestDto requestDto, CustomUser customUser);
}
