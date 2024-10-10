package project.server.service.Implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.*;
import project.server.dto.request.post.UpdatePostRequestDto;
import project.server.dto.request.post.UploadPostRequestDto;
import project.server.dto.request.post.WriteCommentRequestDto;
import project.server.dto.response.ResponseDto;
import project.server.dto.response.post.*;
import project.server.repository.*;
import project.server.security.domain.CustomUser;
import project.server.service.PostService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final EmotionRepository emotionRepository;
    private final CommentRepository commentRepository;
    private final SearchLogRepository searchLogRepository;

    /**
     * 특정 게시물 가져오기
     */
    @Override
    @Transactional
    public ResponseEntity<? super GetPostResponseDto> getPost(int postId) {

        Post post;

        try {
            post = postRepository.findById(postId);

            if (post == null) {
                return GetPostResponseDto.noExistPost();
            }

            //클라이언트에서 접근할 때 4번의 렌더링을 거친다.
            //post.addViewCount();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetPostResponseDto.success(post);
    }

    /**
     * 게시물 업로드
     */
    @Override
    @Transactional
    public ResponseEntity<? super UploadPostResponseDto> uploadPost(UploadPostRequestDto request, CustomUser customUser) {
        try {
            User user = customUser.getUser();
            if(user == null) {
                return UploadPostResponseDto.noExistUser();
            }

            Post post = new Post(request, user);
            postRepository.save(post);

            List<String> postImageList = request.getPostImageList();
            List<PostImage> postImages = new ArrayList<>();

            for(String image : postImageList) {
                PostImage postImage = new PostImage(post, image);
                postImages.add(postImage);
            }
            postImageRepository.saveAll(postImages);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return UploadPostResponseDto.success();
    }

    /**
     * 감정표현 등록하기
     */
    @Override
    @Transactional
    public ResponseEntity<? super PutEmotionResponseDto> putEmotion(int postId, String emotionStatus, CustomUser customUser) {
        try{
            User user = customUser.getUser();

            if(user == null) {
                return PutEmotionResponseDto.noExistUser();
            }

            Post post = postRepository.findById(postId);

            if(post==null) {
                return PutEmotionResponseDto.noExistPost();
            }

            Emotion emotion = emotionRepository.findById(postId, user.getId());

            if(emotion==null) {
                emotion = new Emotion(user, post, emotionStatus);
                emotionRepository.save(emotion);
            }
            else {
                emotion.removeEmotion();
                emotionRepository.delete(postId, user.getId());
            }

        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PutEmotionResponseDto.success();
    }

    /**
     * 감정표현 리스트 가져오기
     */
    @Override
    public ResponseEntity<? super GetEmotionsResponseDto> getEmotions(int postId) {

        Post post;

        try {
            post = postRepository.findById(postId);
            if (post == null) {
                return GetEmotionsResponseDto.noExistPost();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetEmotionsResponseDto.success(post);
    }

    /**
     * 댓글 등록하기
     */
    @Override
    @Transactional
    public ResponseEntity<? super WriteCommentResponseDto> writeComment(WriteCommentRequestDto request, int postId, CustomUser customUser) {
        try {
            User user = customUser.getUser();
            if(user == null) {
                return WriteCommentResponseDto.noExistUser();
            }
            Post post = postRepository.findById(postId);
            if(post==null) {
                return WriteCommentResponseDto.noExistPost();
            }

            Comment comment = new Comment(request, post, user);
            commentRepository.save(comment);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return WriteCommentResponseDto.success();
    }

    /**
     * 댓글 리스트 가져오기
     */
    @Override
    public ResponseEntity<? super GetCommentsResponseDto> getComments(int postId) {
        Post post;

        try {
            post = postRepository.findById(postId);
            if (post == null) {
                return GetCommentsResponseDto.noExistPost();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetCommentsResponseDto.success(post);
    }

    /**
     * 최신 게시물 리스트 가져오기
     */
    @Override
    public ResponseEntity<? super GetLatestPostListResponseDto> getLatestPosts() {
        List<Post> posts;

        try {
            posts = postRepository.findRecentPosts(100);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetLatestPostListResponseDto.success(posts);
    }

    /**
     * 상위 3 게시물 리스트 가져오기
     */
    @Override
    public ResponseEntity<? super GetTop3PostListResponseDto> getTop3Posts() {
        List<Post> posts;

        try {
            Date beforeWeek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sevenDaysAgo = simpleDateFormat.format(beforeWeek);

            posts = postRepository.findTop3Posts(sevenDaysAgo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetTop3PostListResponseDto.success(posts);
    }

    /**
     * 검색 게시물 리스트 가져오기
     */
    @Override
    public ResponseEntity<? super GetSearchPostListResponseDto> getSearchPosts(String searchWord, String preSearchWord) {

        List<Post> posts;

        try {
            posts = postRepository.findBySearchWord(searchWord);

            //검색어 기록 엔티티 생성
            SearchLog searchLog = new SearchLog(searchWord, preSearchWord, false);
            searchLogRepository.save(searchLog);

            boolean relation = preSearchWord != null;
            if(relation) {
                searchLog = new SearchLog(preSearchWord, searchWord, relation);
                searchLogRepository.save(searchLog);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSearchPostListResponseDto.success(posts);
    }

    /**
     * 조회수 증가
     */
    @Override
    @Transactional
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(int postId) {

        Post post;

        try {
            post = postRepository.findById(postId);

            if (post == null) {
                return IncreaseViewCountResponseDto.noExistPost();
            }

            post.addViewCount();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IncreaseViewCountResponseDto.success();
    }

    /**
     * 게시물 삭제하기
     */
    @Override
    @Transactional
    public ResponseEntity<? super DeletePostResponseDto> deletePost(int postId, CustomUser customUser) {
        try {
            User user = customUser.getUser();

            if(user == null) {
                return DeletePostResponseDto.noExistUser();
            }

            Post post = postRepository.findById(postId);

            if(post==null) {
                return DeletePostResponseDto.noExistPost();
            }

            if(!user.getLoginId().equals(post.getAuthorId())) {
                return DeletePostResponseDto.noPermission();
            }

            postRepository.delete(postId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeletePostResponseDto.success();
    }

    /**
     * 게시물 수정하기
     */
    @Override
    @Transactional
    public ResponseEntity<? super UpdatePostResponseDto> updatePost(int postId, UpdatePostRequestDto request, CustomUser customUser) {
        try {

            User user = customUser.getUser();

            if(user == null) {
                return DeletePostResponseDto.noExistUser();
            }

            Post post = postRepository.findById(postId);

            if(post==null) {
                return DeletePostResponseDto.noExistPost();
            }

            if(!user.getLoginId().equals(post.getAuthorId())) {
                return DeletePostResponseDto.noPermission();
            }

            post.updatePost(request);
            postImageRepository.deleteByPostId(postId);

            List<String> postImageList = request.getPostImageList();
            List<PostImage> postImages = new ArrayList<>();

            for(String image : postImageList) {
                PostImage postImage = new PostImage(post, image);
                postImages.add(postImage);
            }
            postImageRepository.saveAll(postImages);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }


        return UpdatePostResponseDto.success();
    }
}
