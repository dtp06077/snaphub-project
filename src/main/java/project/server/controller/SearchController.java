package project.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.server.dto.response.search.GetPopularSearchListResponseDto;
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
}
