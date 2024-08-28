package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.request.post.WritePostRequestDto;
import project.server.dto.response.post.WritePostResponseDto;
import project.server.security.domain.CustomUser;

public interface PostService {
    ResponseEntity<? super WritePostResponseDto> writePost(WritePostRequestDto requestDto, CustomUser customUser);
}
