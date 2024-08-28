package project.server.service.Implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.server.domain.Post;
import project.server.domain.PostImage;
import project.server.domain.User;
import project.server.dto.request.post.WritePostRequestDto;
import project.server.dto.response.ResponseDto;
import project.server.dto.response.post.WritePostResponseDto;
import project.server.repository.PostImageRepository;
import project.server.repository.PostRepository;
import project.server.repository.UserRepository;
import project.server.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    @Override
    @Transactional
    public ResponseEntity<? super WritePostResponseDto> writePost(WritePostRequestDto request, String loginId) {
        try {
            User user = userRepository.findByLoginId(loginId);
            if(user == null) {
                return WritePostResponseDto.notExistUser();
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

        return WritePostResponseDto.success();
    }
}
