package project.server.service.Implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.server.dto.response.ResponseDto;
import project.server.dto.response.search.GetPopularSearchListResponseDto;
import project.server.dto.response.search.GetRelatableSearchListResponseDto;
import project.server.repository.SearchLogRepository;
import project.server.service.SearchService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {

    private final SearchLogRepository searchLogRepository;

    @Override
    public ResponseEntity<? super GetPopularSearchListResponseDto> getPopularSearchList() {

        List<String> popularSearchList;

        try {
            popularSearchList = searchLogRepository.findPopularSearch();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetPopularSearchListResponseDto.success(popularSearchList);
    }

    @Override
    public ResponseEntity<? super GetRelatableSearchListResponseDto> getRelatableSearchList(String searchWord) {

        List<String> relatableSearchList;

        try {
            relatableSearchList = searchLogRepository.findRelationSearch(searchWord);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetRelatableSearchListResponseDto.success(relatableSearchList);
    }
}
