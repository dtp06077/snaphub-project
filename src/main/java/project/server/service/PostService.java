package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.post.WritePostRequestDto;
import project.server.dto.response.post.WritePostResponseDto;

public interface PostService {
    ResponseEntity<? super WritePostResponseDto> writePost(WritePostRequestDto requestDto, String loginId);
}
