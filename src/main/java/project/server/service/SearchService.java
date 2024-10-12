package project.server.service;

import org.springframework.http.ResponseEntity;
import project.server.dto.response.search.GetPopularSearchListResponseDto;
import project.server.dto.response.search.GetRelatableSearchListResponseDto;

public interface SearchService {

    //인기 검색어 리스트 불러오기
    ResponseEntity<? super GetPopularSearchListResponseDto> getPopularSearchList();

    //연관 검색어 리스트 불러오기
    ResponseEntity<? super GetRelatableSearchListResponseDto> getRelatableSearchList(String searchWord);
}
