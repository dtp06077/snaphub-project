package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.response.search.GetPopularSearchListResponseDto;

public interface SearchService {

    ResponseEntity<? super GetPopularSearchListResponseDto> getPopularSearchList();
}
