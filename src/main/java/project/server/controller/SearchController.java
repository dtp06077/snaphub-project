package project.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.server.dto.response.search.GetPopularSearchListResponseDto;
import project.server.dto.response.search.GetRelatableSearchListResponseDto;
import project.server.service.SearchService;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * 인기 검색어 리스트 검색
     */
    @GetMapping("/popular-list")
    public ResponseEntity<? super GetPopularSearchListResponseDto> getPopularSearchList() {
        return searchService.getPopularSearchList();
    }

    /**
     * 연관 검색어 리스트 검색
     */
    @GetMapping("/{searchWord}/relation-list")
    public ResponseEntity<? super GetRelatableSearchListResponseDto> getRelatableSearchList(
            @PathVariable("searchWord") String searchWord) {
        return searchService.getRelatableSearchList(searchWord);
    }
}
