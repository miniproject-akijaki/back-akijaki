package com.sparta.akijaki.controller;

import com.sparta.akijaki.dto.*;
import com.sparta.akijaki.service.AwsS3Service;
import com.sparta.akijaki.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Api(tags = {"Post API"})
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    private final AwsS3Service awsS3Service;


    // 전체 게시글 조회
    @GetMapping("/post")
    @ApiOperation(value = "전체 게시글 조회")
    public PostAllShowResponseDto getPosts() {
        return postService.getPosts();
    }

    // 게시글 상세 조회
    @GetMapping("/post/{id}")
    @ApiOperation(value = "게시글 상세 조회")
    public PostShowResponseDto getPost(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return postService.getPost(id,httpServletRequest);
    }

    //게시글 작성
    @PostMapping("/post")
    @ApiOperation(value = "게시글 작성")
    public PostCreateResponseDto createPost(@RequestParam("title") String title, @RequestParam("content") String content,
                                            @RequestParam("price") int price, @RequestPart(value = "file",required = false) List<MultipartFile> multipartFiles, HttpServletRequest request)  {
        String imageUrl = awsS3Service.uploadFile(multipartFiles).get(0);
        return postService.createPost(title, content, price, imageUrl, request);
    }

//    @PostMapping("/post")
//    @ApiOperation(value = "게시글 작성")
//    public void createPost(@ModelAttribute TestDto testDto, HttpServletRequest request)  {
//        String imageUrl = awsS3Service.uploadFile(postCreateResponseDto.getFiles()).get(0);
//        System.out.println(testDto.getFiles());
//        System.out.println(testDto.getTitle());
//        return postService.createPost();
//    }

    // 게시글 수정
    @PutMapping("/post/{id}")
    @ApiOperation(value = "게시글 수정")
    public PostUpdateResponseDto updatePost(@PathVariable Long id, @Valid @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }

    // 게시글 삭제
    @DeleteMapping("/post/{id}")
    @ApiOperation(value = "게시글 삭제")
    public CompleteResponseDto deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }
}
