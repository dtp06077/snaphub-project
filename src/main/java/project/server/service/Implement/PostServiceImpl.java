package project.server.service.Implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.Post;
import project.server.domain.PostImage;
import project.server.domain.User;
import project.server.dto.request.post.PostUploadRequestDto;
import project.server.dto.response.ResponseDto;
import project.server.dto.response.post.GetPostResponseDto;
import project.server.dto.response.post.PostUploadResponseDto;
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

    @Override
    public ResponseEntity<? super GetPostResponseDto> getPost(int postId) {

        Post post;

        try {
            post = postRepository.findById(postId);

            if (post == null) {
                return GetPostResponseDto.noExistPost();
            }

            post.addViewCount();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetPostResponseDto.success(post);
    }

    @Transactional
    @Override
    public ResponseEntity<? super PostUploadResponseDto> writePost(PostUploadRequestDto request, CustomUser customUser) {
        try {
            User user = customUser.getUser();
            if(user == null) {
                return PostUploadResponseDto.notExistUser();
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

        return PostUploadResponseDto.success();
    }
}
