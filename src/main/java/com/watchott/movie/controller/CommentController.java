package com.watchott.movie.controller;

import com.watchott.common.dto.ApiResponse;
import com.watchott.movie.dto.CommentDto;
import com.watchott.user.entity.User;
import com.watchott.movie.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : watchott.controller
 * fileName       : CommentController
 * author         : shipowner
 * date           : 2023-10-01
 */

@RestController
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/save")
    public ApiResponse saveComment(CommentDto commentDto
            , Authentication authentication){

        User user = (User)authentication.getPrincipal();
        commentService.save(commentDto, user.getUserId());
        return ApiResponse.success("댓글 작성이 완료되었습니다");
    }

    @GetMapping("/{movieId}")
    public ApiResponse<List<CommentDto>> getCommentsByMovieId(@PathVariable("movieId") Integer movieId) {
        return ApiResponse.success(commentService.findCommentByMovieId(movieId));
    }

}
