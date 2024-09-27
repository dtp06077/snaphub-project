package project.server.service.Implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.*;
import project.server.dto.request.post.UploadPostRequestDto;
import project.server.dto.request.post.WriteCommentRequestDto;
import project.server.dto.response.ResponseDto;
import project.server.dto.response.post.*;
import project.server.repository.CommentRepository;
import project.server.repository.EmotionRepository;
import project.server.repository.PostImageRepository;
import project.server.repository.PostRepository;
import project.server.security.domain.CustomUser;
import project.server.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final EmotionRepository emotionRepository;
    private final CommentRepository commentRepository;

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

    @Override
    @Transactional
    public ResponseEntity<? super WriteCommentResponseDto> writeComment(WriteCommentRequestDto requestDto, int postId, CustomUser customUser) {
        try {
            User user = customUser.getUser();
            if(user == null) {
                return WriteCommentResponseDto.noExistUser();
            }
            Post post = postRepository.findById(postId);
            if(post==null) {
                return WriteCommentResponseDto.noExistPost();
            }

            Comment comment = new Comment(requestDto, post, user);
            commentRepository.save(comment);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return WriteCommentResponseDto.success();
    }

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

            if(user.getLoginId() != post.getAuthorId()) {
                return DeletePostResponseDto.noPermission();
            }

            postRepository.delete(postId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeletePostResponseDto.success();
    }
}
