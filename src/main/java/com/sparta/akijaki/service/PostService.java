package com.sparta.akijaki.service;

import com.sparta.akijaki.dto.*;
import com.sparta.akijaki.entity.*;
import com.sparta.akijaki.repository.CommentLikesRepository;
import com.sparta.akijaki.repository.CommentRepository;
import com.sparta.akijaki.repository.PostLikesRepository;
import com.sparta.akijaki.repository.PostRepository;
import com.sparta.akijaki.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikesRepository postLikesRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final UserUtil userUtil;
    private final AwsS3Service awsS3Service;


    // 전체 포스트 가져오기
    @Transactional(readOnly = true)
    public PostAllShowResponseDto getPosts() {
        PostAllShowResponseDto postAllShowResponseDto = new PostAllShowResponseDto();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();

        for(Post post : posts) {
            // 게시글 좋아요 count
            Long postLikeCnt = postLikesRepository.countByPostAndLikeCheckIsTrue(post);
            CommentListResponseDto commentListRequestDto = new CommentListResponseDto();
            List<Comment> comments = commentRepository.findAllByPostIdWithUser(post.getId());
            for(Comment comment : comments) {
                // 댓글 좋아요 count
                Long commentLikeCnt = commentLikesRepository.countByCommentAndLikeCheckIsTrue(comment);
                commentListRequestDto.addComment(new CommentResponseDto(comment, commentLikeCnt));
            }
            postAllShowResponseDto.addPost(new PostResponseDto(post, commentListRequestDto, postLikeCnt));
        }

        return postAllShowResponseDto;
    }

    // 선택 포스트 가져오기
    @Transactional(readOnly = true)
    public PostShowResponseDto getPost(Long id, HttpServletRequest httpServletRequest) {
        User user = userUtil.getUserInfo(httpServletRequest);
        Post post = checkPost(id);
        Long postLikeCnt = postLikesRepository.countByPostAndLikeCheckIsTrue(post);

        CommentListResponseDto commentListRequestDto = new CommentListResponseDto();
        List<Comment> comments = commentRepository.findAllByPostIdWithUser(post.getId());
        //선택 포스트에 대한 댓글 가져오기
        for(Comment comment : comments) {
            //해당 댓글 좋아요 개수 가져오기
            Long commentLikeCnt = commentLikesRepository.countByCommentAndLikeCheckIsTrue(comment);
            //해당 댓글에 대한 유저의 좋아요 여부 확인(눌렀을때 true, 안눌렀을때 false 반환)
            CommentLikes commentLikes = commentLikesRepository.findByUserAndComment(user, comment).orElse(null);
            if(commentLikes==null){
                commentListRequestDto.addComment(new CommentResponseDto(comment, false,commentLikeCnt));
            }else{
            commentListRequestDto.addComment(new CommentResponseDto(comment, commentLikes.isLikeCheck(),commentLikeCnt));
            }
        }
        PostLikes postLikes = postLikesRepository.findByUserAndPost(user, post).orElse(null);
        if (postLikes==null) {
            return new PostShowResponseDto(post, false,commentListRequestDto, postLikeCnt);
        }
        return new PostShowResponseDto(post, postLikes.isLikeCheck(),commentListRequestDto, postLikeCnt);
    }

    // 포스트 생성
    @Transactional
    public PostCreateResponseDto createPost(String title,String content,int price,String imageUrl, HttpServletRequest request) {
        User user = userUtil.getUserInfo(request);
        Post post = new Post(title,content,price,imageUrl, user);
        postRepository.save(post); // 자동으로 쿼리가 생성되면서 데이터베이스에 연결되며 저장된다.

        return new PostCreateResponseDto(post);
    }

    // 포스트 수정
    @Transactional
    public PostUpdateResponseDto updatePost(Long id, String title, String content, int price, String imageUrl, HttpServletRequest request) {
        Post post = checkPost(id);
        User user = userUtil.getUserInfo(request);
        UserRoleEnum userRoleEnum = user.getRole();
        Long postLikeCnt = postLikesRepository.countByPostAndLikeCheckIsTrue(post);

        // 게시글 작성자이거나 관리자인 경우
        if(post.getUser().getUsername().equals(user.getUsername()) || userRoleEnum.equals(UserRoleEnum.ADMIN)) {
//            awsS3Service.deleteFile(post.getImageUrl().replace("https://akijaki-s3-bucket.s3.ap-northeast-2.amazonaws.com/",""));
            post.update(title, content, price, imageUrl);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("포스트 작성자가 아니라서 수정할 수 없습니다.");
        }
        return new PostUpdateResponseDto(post, postLikeCnt);
    }

    // 포스트 삭제
    @Transactional
    public CompleteResponseDto deletePost(Long id, HttpServletRequest request) {
        Post post = checkPost(id);
        User user = userUtil.getUserInfo(request);
        UserRoleEnum userRoleEnum = user.getRole();

        // 게시글 작성자이거나 관리자인 경우
        if(post.getUser().getUsername().equals(user.getUsername()) || userRoleEnum.equals(UserRoleEnum.ADMIN)) {
                postRepository.delete(post);
            } else {
                throw new IllegalArgumentException("포스트 작성자가 아니라서 삭제할 수 없습니다.");
            }
        return new CompleteResponseDto("포스트 삭제 성공");
    }

    // 포스트 번호를 체크해서 번호가 없으면 에러메세지 출력
    private Post checkPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("포스트가 존재하지 않습니다.")
        );
    }

    // ** 쿼리 줄여주는 JPQL 사용
    // cascade = CascadeType.REMOVE대신 마지막 삭제 위치부터 하나씩 삭제하기
    // 1. 포스트 아이디를 받아서 그 포스트 번호에 있는 댓글좋아요 지우기
    // 2. 댓글 지우기
    // 3. 포스트 좋아요 지우기
    private void deletePostByPostId(Long id) {
        Post post = checkPost(id);
        List<Long> commentIds = commentRepository.findIdsByPostId(id);
        commentLikesRepository.deleteByCommentId(commentIds);
        commentRepository.deleteByCommentId(commentIds);
        postLikesRepository.deleteByPostId(id);
        postRepository.delete(post);
    }

}
